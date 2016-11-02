package com.harishkannarao.config

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object HttpConfiguration {

  val httpConf = http
    .baseURL(PropertiesUtil.getInstance.getApplicationUrl) // Here is the root for all relative URLs
    .shareConnections
    .contentTypeHeader("application/json")

}
