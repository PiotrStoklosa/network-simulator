package com.nokia.uwr.scenario.ue;

import com.nokia.uwr.board.Localization;

/**
 * @param turn         turn number
 * @param localization new localization of the UE
 * @param action       action which UE take
 * @author MiSobecki
 */
public record UEStep(int turn, Localization localization, String action) {
}
