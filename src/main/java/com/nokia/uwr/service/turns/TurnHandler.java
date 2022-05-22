package com.nokia.uwr.service.turns;

import com.nokia.uwr.scenario.ScenarioSchema;

/**
 * Interface for classes that performs actions for certain turns.
 *
 * @author najedzony
 */

public interface TurnHandler {

    /**
     * @param turnNumber Turn number.
     * @author Piotr Stoklosa
     */
    void initializeTurn(int turnNumber);

    /**
     * @param TurnNumber Turn number that we want to find and perform actions for.
     * @author najedzony
     */
    void findAndDoActionsForThisTurn(int TurnNumber);

    /**
     * @param scenario Scenario object that we want to get.
     * @author najedzony
     */
    void setScenario(ScenarioSchema scenario);
}
