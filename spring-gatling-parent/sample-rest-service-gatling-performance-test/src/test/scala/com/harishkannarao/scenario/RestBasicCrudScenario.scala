package com.harishkannarao.scenario

import com.harishkannarao.config.{HttpConfiguration, PropertiesUtil}
import io.gatling.core.Predef._
import io.gatling.core.structure.PopulatedScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration
import scala.concurrent.duration.FiniteDuration

object RestBasicCrudScenario {
  val basicCrudScenarioName: String = "Basic CRUD operations"
  private val basicCrudOperations = scenario(basicCrudScenarioName) // A scenario is a chain of requests and pauses
    .group(basicCrudScenarioName) {
    exec(http("Create Customer")
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
  }

  val basicCrudScenario: PopulatedScenarioBuilder = basicCrudOperations.inject(
    // if no of concurrent request per second is 4, then rampUsers = (no of concurrent request per second * total duration of simulation in seconds)
    rampUsers(PropertiesUtil.getInstance.getBasicCrudRestScenarioPerSecond.toInt * PropertiesUtil.getInstance.getBasicCrudRestScenarioDurationInSecond.toInt) over (new FiniteDuration(PropertiesUtil.getInstance.getBasicCrudRestScenarioDurationInSecond.toLong, duration.SECONDS))
  ).protocols(HttpConfiguration.httpConf)
}
