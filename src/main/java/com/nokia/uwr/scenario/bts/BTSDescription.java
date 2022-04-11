package com.nokia.uwr.scenario.bts;

import com.nokia.uwr.board.Localization;

/**
 * @param name         name of the BTS
 * @param localization the location on the board
 * @param signalPower  signal power of the BTS
 * @author MiSobecki
 */
public record BTSDescription(String name, Localization localization, int signalPower) {
}
