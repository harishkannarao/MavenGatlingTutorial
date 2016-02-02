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

class SampleWebAppSimulation extends Simulation {
  val logger: Logger = LoggerFactory.getLogger(classOf[SampleWebAppSimulation])
  val propertiesUtil: PropertiesUtil = SpringUtil.ctx.getBean(classOf[PropertiesUtil])

  val httpConf = http
    .baseURL(propertiesUtil.getWebApplicationUrl) // Here is the root for all relative URLs

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

  setUp(
    basicCrudOperations.inject(
      rampUsers(propertiesUtil.getNoOfRequestsPerSecond.toInt * propertiesUtil.getTotalDurationInSeconds.toInt) over(new FiniteDuration(propertiesUtil.getTotalDurationInSeconds.toLong, duration.SECONDS))
    ).protocols(httpConf)
  )
  .assertions(
    global.responseTime.mean.lessThan(2),
    global.successfulRequests.percent.greaterThan(95)
  )
}
