package com.harishkannarao.simulation

import com.harishkannarao.config.PropertiesUtil
import com.harishkannarao.util.SpringUtil
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration
import scala.concurrent.duration._

class SampleRestServiceSimulation extends Simulation {
  val propertiesUtil: PropertiesUtil = SpringUtil.ctx.getBean(classOf[PropertiesUtil])

  val httpConf = http
    .baseURL(propertiesUtil.getApplicationUrl) // Here is the root for all relative URLs
    .contentTypeHeader("application/json")

  val scn = scenario("Basic CRUD operations") // A scenario is a chain of requests and pauses
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
      .body(StringBody(
      """
        {"firstName":"Harish U","lastName":"Kannarao U","id":"${id}"}
      """.stripMargin
      ))
      .check(status.is(200))
    )
    .pause(new FiniteDuration(1, duration.SECONDS))
    .exec(http("Delete Customer")
      .delete("/customer/${id}")
      .check(status.is(200))
    )

  setUp(
    scn.inject(
      // if no of concurrent request per second is 4, then rampUsers = (no of concurrent request per second * total duration of simulation in seconds)
      rampUsers(propertiesUtil.getNoOfRequestsPerSecond.toInt * propertiesUtil.getTotalDurationInSeconds.toInt) over(new FiniteDuration(propertiesUtil.getTotalDurationInSeconds.toLong, duration.SECONDS))
    ).protocols(httpConf)
  )
  .assertions(
    global.responseTime.mean.lessThan(2),
    global.successfulRequests.percent.greaterThan(95)
  )
}
