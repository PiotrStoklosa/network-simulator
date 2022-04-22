package com.nokia.uwr.scenario.ue;

import com.nokia.uwr.model.UE;

import java.util.List;

/**
 * @param ue  UE object
 * @param steps steps, which UE will take in turns
 * @author MiSobecki
 */
public record UEScenario(UE ue, List<UEStep> steps) {
}
