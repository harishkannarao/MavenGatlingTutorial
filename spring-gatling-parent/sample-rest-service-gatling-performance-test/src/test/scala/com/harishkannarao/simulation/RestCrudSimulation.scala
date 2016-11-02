package com.harishkannarao.simulation

import com.harishkannarao.scenario.RestBasicCrudScenario
import io.gatling.core.Predef._

class RestCrudSimulation extends Simulation {
  setUp(
    RestBasicCrudScenario.basicCrudScenario
  )
}
