package com.nokia.uwr.scenario.ue;

import java.util.List;

/**
 * @param name  name of the UE
 * @param steps steps, which UE will take in turns
 * @author MiSobecki
 */
public record UEScenario(String name, List<UEStep> steps) {
}
