package com.harishkannarao.simulation

import com.harishkannarao.config.PropertiesUtil
import com.harishkannarao.util.SpringUtil
import io.gatling.core.Predef._
import io.gatling.core.feeder.{FeederBuilder, RecordSeqFeederBuilder}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import org.slf4j.{LoggerFactory, Logger}

import scala.concurrent.duration
import scala.concurrent.duration._
import scala.util.Random

class SampleRestServiceSimulation extends Simulation {
  val logger: Logger = LoggerFactory.getLogger(classOf[SampleRestServiceSimulation])
  val propertiesUtil: PropertiesUtil = SpringUtil.ctx.getBean(classOf[PropertiesUtil])

  val httpConf = http
    .baseURL(propertiesUtil.getApplicationUrl) // Here is the root for all relative URLs
    .contentTypeHeader("application/json")

  val basicCrudOperations = scenario("Basic CRUD operations") // A scenario is a chain of requests and pauses
    .exec(http("Create Customer")
      .put("/customer")
      .body(StringBody(
        """
          {"firstName":"Harish","lastName":"Kannarao"}
        """.stripMargin))
      .check(status.is(201))
      .check(jsonPath("$.id").saveAs("id"))
    )
    .pause(new FiniteDuration(1, duration.SECONDS)) // Note that Gatling has recorder real time pauses
    .exec(http("Get Customer")
      .get("/customer/${id}")
      .check(status.is(200))
    )
    .pause(new FiniteDuration(1, duration.SECONDS))
    .exec(http("Update Customer")
      .post("/customer")
      .body(ELFileBody("customer/updateCustomer.json")).asJSON
      .check(status.is(200))
    )
    .pause(new FiniteDuration(1, duration.SECONDS))
    .exec(http("Delete Customer")
      .delete("/customer/${id}")
      .check(status.is(200))
    )


  def createSumCalculatorScenario(scenarioName: String, feederBuilder: FeederBuilder[_]): ScenarioBuilder = {
    scenario(scenarioName)
      .feed(feederBuilder)
      .exec(
        http("Add two values")
          .get("/calculator/sum")
          .queryParam("value1", "${value1}")
          .queryParam("value2", "${value2}")
          .check(status.is(200))
      )
  }

  val staticFeeder = Array(
    Map("value1" -> "1", "value2" -> "1"),
    Map("value1" -> "2", "value2" -> "2"),
    Map("value1" -> "3", "value2" -> "3")).random

  logger.debug(s"Static Feeder Values: $staticFeeder")

  def getRandomIntegerAsString(range: Int): String = {
    Random.nextInt(range).toString
  }

  val dynamicFeeder = Iterator.continually(
    Map("value1" -> getRandomIntegerAsString(100), "value2" -> getRandomIntegerAsString(100))
  )

  val sumCalculatorWithStaticFeeder = createSumCalculatorScenario("Addition of two integers with static feeder", staticFeeder)

  val sumCalculatorWithDynamicFeeder = createSumCalculatorScenario("Addition of two integers with dynamic feeder", dynamicFeeder)

  setUp(
    basicCrudOperations.inject(
      // if no of concurrent request per second is 4, then rampUsers = (no of concurrent request per second * total duration of simulation in seconds)
      rampUsers(propertiesUtil.getNoOfRequestsPerSecond.toInt * propertiesUtil.getTotalDurationInSeconds.toInt) over(new FiniteDuration(propertiesUtil.getTotalDurationInSeconds.toLong, duration.SECONDS))
    ).protocols(httpConf),
    sumCalculatorWithStaticFeeder.inject(
      rampUsers(propertiesUtil.getNoOfRequestsPerSecond.toInt * propertiesUtil.getTotalDurationInSeconds.toInt) over(new FiniteDuration(propertiesUtil.getTotalDurationInSeconds.toLong, duration.SECONDS))
    ).protocols(httpConf),
    sumCalculatorWithDynamicFeeder.inject(
      rampUsers(propertiesUtil.getNoOfRequestsPerSecond.toInt * propertiesUtil.getTotalDurationInSeconds.toInt) over(new FiniteDuration(propertiesUtil.getTotalDurationInSeconds.toLong, duration.SECONDS))
    ).protocols(httpConf)
  )
  .assertions(
    global.responseTime.mean.lessThan(2),
    global.successfulRequests.percent.greaterThan(95)
  )
}
