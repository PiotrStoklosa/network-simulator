package com.nokia.uwr.scenario;

import com.nokia.uwr.scenario.bts.BTSDescription;
import com.nokia.uwr.scenario.ue.UEScenario;

import java.util.List;

/**
 * @param btsDescriptions list of BTS's
 * @param ueScenarios     list of UE Scenarios
 * @author MiSobecki
 */
public record ScenarioSchema(List<BTSDescription> btsDescriptions, List<UEScenario> ueScenarios) {
}
