package com.harishkannarao.simulation

import com.harishkannarao.config.PropertiesUtil
import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration
import scala.concurrent.duration._

class SampleWebAppSimulation extends Simulation {
  val logger: Logger = LoggerFactory.getLogger(classOf[SampleWebAppSimulation])
  val propertiesUtil: PropertiesUtil = new PropertiesUtil()

  val httpConf = http
    .baseURL(propertiesUtil.getWebApplicationUrl) // Here is the root for all relative URLs
    .shareConnections

  private val basicCrudScenarioName: String = "Basic CRUD operations"
  val basicCrudOperationsScenario = scenario(basicCrudScenarioName) // A scenario is a chain of requests and pauses
    .group(basicCrudScenarioName) {
      exec(http("Go to home page")
        .get("")
        .check(status.is(200))
      )
      .pause(new FiniteDuration(2, duration.SECONDS)) // Note that Gatling has recorder real time pauses
      .exec(http("Go to create person page")
        .get("/person/create")
        .check(status.is(200))
      )
      .pause(new FiniteDuration(2, duration.SECONDS))
      .exec(http("Create a person")
        .post("/person/save")
        .header("Content-Type", """application/x-www-form-urlencoded""")
        .formParam("name", "Harish")
        .formParam("email", "harish.gtec@gmail.com")
        .formParam("password", "12345")
        .formParam("mobile", "1234567890")
        .check(status.is(200))
        .check(css("a.btn.btn-warning:last-of-type", "href").saveAs("editUrl"))
      )
      .pause(new FiniteDuration(2, duration.SECONDS))
      .exec(http("Edit a person")
        .get("${editUrl}")
        .check(status.is(200))
        .check(css("input[name='id']", "value").saveAs("id"))
      )
      .pause(new FiniteDuration(2, duration.SECONDS))
      .exec(http("Save a person")
        .post("/person/update")
        .header("Content-Type", """application/x-www-form-urlencoded""")
        .formParam("id", "${id}")
        .formParam("name", "Harish123")
        .formParam("email", "harish.gtec@gmail.com")
        .formParam("password", "12345")
        .formParam("mobile", "0987654321")
        .check(status.is(200))
      )
  }

  val basicCrudOperationsPopulatedScenario = basicCrudOperationsScenario
    .inject(
      rampUsers (
        propertiesUtil.getBasicCrudWebScenarioPerSecond.toInt *
          propertiesUtil.getBasicCrudWebScenarioDurationInSecond.toInt
      ) over (
        new FiniteDuration(
          propertiesUtil.getBasicCrudWebScenarioDurationInSecond.toLong, duration.SECONDS
        )
      )
    )
    .protocols(
      httpConf
    )

  val scenarioBuildersMap: Map[String, PopulationBuilder] = Map(
    basicCrudScenarioName -> basicCrudOperationsPopulatedScenario
  )

  val scenarioName: String = System.getProperty("scenarioName")

  def filterScenarios(scenariosMap: Map[String, PopulationBuilder], scenarioName: String): List[PopulationBuilder] = {
    if(scenarioName != null) {
      scenarioBuildersMap.filterKeys(keyName => keyName.equals(scenarioName)).values.toList
    } else {
      scenarioBuildersMap.values.toList
    }
  }

  val scenarioBuilders: List[PopulationBuilder] = filterScenarios(scenarioBuildersMap, scenarioName)

  setUp(
    scenarioBuilders
  )
  .assertions(
    global.responseTime.mean.lessThan(30),
    global.successfulRequests.percent.greaterThan(95)
  )
}
