package com.harishkannarao.simulation

import com.harishkannarao.scenario.{RestBasicCrudScenario, RestSumCalculatorScenario}
import io.gatling.core.Predef._
import io.gatling.core.structure.PopulatedScenarioBuilder

class SampleRestServiceSimulation extends Simulation {

  val scenarioBuildersMap: Map[String, PopulatedScenarioBuilder] = Map(
    RestBasicCrudScenario.basicCrudScenarioName -> RestBasicCrudScenario.basicCrudScenario,
    RestSumCalculatorScenario.additionWithStaticFeederName -> RestSumCalculatorScenario.sumCalculatorStaticFeederScenario,
    RestSumCalculatorScenario.additionWithJsonFeederName -> RestSumCalculatorScenario.sumCalculatorJsonFeederScenario,
    RestSumCalculatorScenario.additionalWithDynaicFeederName -> RestSumCalculatorScenario.sumCalculatorDynamicFeederScenario
  )

  val scenarioName: String = System.getProperty("scenarioName")

  def filterScenarios(scenariosMap: Map[String, PopulatedScenarioBuilder], scenarioName: String): List[PopulatedScenarioBuilder] = {
    if(scenarioName != null) {
      scenarioBuildersMap.filterKeys(keyName => keyName.equals(scenarioName)).values.toList
    } else {
      scenarioBuildersMap.values.toList
    }
  }

  val scenarioBuilders: List[PopulatedScenarioBuilder] = filterScenarios(scenarioBuildersMap, scenarioName)

  setUp(
    scenarioBuilders
  )
  .assertions(
    global.responseTime.mean.lessThan(10),
    global.successfulRequests.percent.greaterThan(95)
  )
}
