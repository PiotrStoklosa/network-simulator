package com.nokia.uwr.service.turns;

import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.scenario.bts.BTSDescription;

import java.util.List;

/**
 * Interface for classes that performs actions for certain turns.
 *
 * @author najedzony
 */

public interface TurnHandler {

    /**
     * Create connection between network-simulator and calls-management-system
     *
     * @param descriptionList descriptionList needed to properly initialize board on calls-management-system side.
     * @author Piotr Stoklosa
     */
    void initializeCall(List<BTSDescription> descriptionList);

    /**
     * Terminate connection between network-simulator and calls-management-system
     *
     * @author Piotr Stoklosa
     */
    void terminateCall();

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
