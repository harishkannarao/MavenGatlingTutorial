package com.harishkannarao.scenario

import com.harishkannarao.config.{HttpConfiguration, PropertiesUtil}
import io.gatling.core.Predef._
import io.gatling.core.feeder.FeederBuilder
import io.gatling.core.structure.{PopulatedScenarioBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration
import scala.concurrent.duration.FiniteDuration
import scala.util.Random

object RestSumCalculatorScenario {

  val logger: Logger = LoggerFactory.getLogger(RestSumCalculatorScenario.getClass)

  def createSumCalculatorScenario(scenarioName: String, feederBuilder: FeederBuilder[_]): ScenarioBuilder = {
    scenario(scenarioName)
      .feed(feederBuilder)
      .group(scenarioName) {
        exec(
          http("Add two values")
            .get("/calculator/sum")
            .queryParam("value1", "${value1}")
            .queryParam("value2", "${value2}")
            .check(status.is(200))
        )
      }
  }

  val staticFeeder = Array(
    Map("value1" -> "1", "value2" -> "1"),
    Map("value1" -> "2", "value2" -> "2"),
    Map("value1" -> "3", "value2" -> "3")).random

  logger.debug(s"Static Feeder Values: $staticFeeder")

  val jsonFileFeeder = jsonFile("calculator/sumValues.json").random

  logger.debug(s"Json File Feeder Values: $jsonFileFeeder")

  def getRandomIntegerAsString(range: Int): String = {
    Random.nextInt(range).toString
  }

  val dynamicFeeder = Iterator.continually(
    Map("value1" -> getRandomIntegerAsString(100), "value2" -> getRandomIntegerAsString(100))
  )

  val additionWithStaticFeederName: String = "Addition of two integers with static feeder"
  val sumCalculatorWithStaticFeeder = createSumCalculatorScenario(additionWithStaticFeederName, staticFeeder)

  val additionWithJsonFeederName: String = "Addition of two integers with json file feeder"
  val sumCalculatorWithJsonFileFeeder = createSumCalculatorScenario(additionWithJsonFeederName, jsonFileFeeder)

  val additionalWithDynaicFeederName: String = "Addition of two integers with dynamic feeder"
  val sumCalculatorWithDynamicFeeder = createSumCalculatorScenario(additionalWithDynaicFeederName, dynamicFeeder)

  val sumCalculatorStaticFeederScenario: PopulatedScenarioBuilder = sumCalculatorWithStaticFeeder.inject(
    rampUsers(PropertiesUtil.getInstance.getSumCalculatorStaticFeederScenarioPerSecond.toInt * PropertiesUtil.getInstance.getSumCalculatorStaticFeederScenarioDurationInSecond.toInt) over (new FiniteDuration(PropertiesUtil.getInstance.getSumCalculatorStaticFeederScenarioDurationInSecond.toLong, duration.SECONDS))
  ).protocols(HttpConfiguration.httpConf)

  val sumCalculatorJsonFeederScenario: PopulatedScenarioBuilder = sumCalculatorWithJsonFileFeeder.inject(
    rampUsers(PropertiesUtil.getInstance.getSumCalculatorJsonFeederScenarioPerSecond.toInt * PropertiesUtil.getInstance.getSumCalculatorJsonFeederScenarioDurationInSecond.toInt) over (new FiniteDuration(PropertiesUtil.getInstance.getSumCalculatorJsonFeederScenarioDurationInSecond.toLong, duration.SECONDS))
  ).protocols(HttpConfiguration.httpConf)

  val sumCalculatorDynamicFeederScenario: PopulatedScenarioBuilder = sumCalculatorWithDynamicFeeder.inject(
    rampUsers(PropertiesUtil.getInstance.getSumCalculatorDynamicFeederScenarioPerSecond.toInt * PropertiesUtil.getInstance.getSumCalculatorDynamicFeederScenarioDurationInSecond.toInt) over (new FiniteDuration(PropertiesUtil.getInstance.getSumCalculatorDynamicFeederScenarioDurationInSecond.toLong, duration.SECONDS))
  ).protocols(HttpConfiguration.httpConf)

}
