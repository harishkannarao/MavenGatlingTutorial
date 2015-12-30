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
    .pause(1) // Note that Gatling has recorder real time pauses
    .exec(http("Get Customer")
      .get("/customer/${id}")
      .check(status.is(200))
    )
    .pause(1)
    .exec(http("Update Customer")
      .post("/customer")
      .body(StringBody(
      """
        {"firstName":"Harish U","lastName":"Kannarao U","id":"${id}"}
      """.stripMargin
      ))
      .check(status.is(200))
    )
    .pause(1)
    .exec(http("Delete Customer")
      .delete("/customer/${id}")
      .check(status.is(200))
    )

  setUp(
    scn.inject(
      constantUsersPerSec(propertiesUtil.getConstantUsersPerSec.toDouble) during(new FiniteDuration(propertiesUtil.getDurationInMinutes.toLong, duration.MINUTES))
    ).protocols(httpConf)
  )
}
