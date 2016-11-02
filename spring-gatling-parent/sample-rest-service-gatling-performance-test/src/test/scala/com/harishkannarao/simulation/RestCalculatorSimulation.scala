package com.harishkannarao.simulation

import com.harishkannarao.scenario.RestSumCalculatorScenario
import io.gatling.core.Predef._

class RestCalculatorSimulation extends Simulation {
  setUp(
    RestSumCalculatorScenario.sumCalculatorStaticFeederScenario,
    RestSumCalculatorScenario.sumCalculatorJsonFeederScenario,
    RestSumCalculatorScenario.sumCalculatorDynamicFeederScenario
  )
}
