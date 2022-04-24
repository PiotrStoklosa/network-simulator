package com.nokia.uwr.service;

import com.nokia.uwr.controller.ApplicationInitializer;
import com.nokia.uwr.scenario.ScenarioSchema;

/**
 * Central service that manage turns
 *
 * @author Piotr Stoklosa
 * @see ApplicationInitializer#startApplication
 */
public interface BoardService {

    /**
     * Initialize board by reading scenario file and manage turns using.
     *
     * @return true if simulation finish successfully, otherwise false
     * @author Piotr Stoklosa
     */
    boolean InitialiseBoardAndManageTurns();

    /**
     * Simulate actions written in given scenarioSchema.
     *
     * @param scenarioSchema scenarioSchema that contains scenario for simulation
     * @author Piotr Stoklosa
     */
    void simulate(ScenarioSchema scenarioSchema);

}
