package com.nokia.uwr.service.turns;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nokia.uwr.board.Board;
import com.nokia.uwr.model.UE;
import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.scenario.ue.UEScenario;
import com.nokia.uwr.scenario.ue.UEStep;
import com.nokia.uwr.service.CalculationSignalServiceImpl;
import com.nokia.uwr.service.parser.SignalParserImpl;
import com.nokia.uwr.service.parser.TurnParser;
import com.nokia.uwr.service.rest.APIClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class that performs actions for certain turns.
 *
 * @author najedzony
 * @author Barbara Moczulska
 */
@Service
public class TurnHandlerImpl implements TurnHandler {

    private static final Logger LOGGER = LogManager.getLogger(TurnHandlerImpl.class);

    private final Board board;
    private ScenarioSchema scenario;
    private final APIClient apiClient;
    private final SignalParserImpl signalParser;
    private final CalculationSignalServiceImpl calculationSignalService;
    private final TurnParser turnParser;

    @Autowired
    public TurnHandlerImpl(Board board,
                           APIClient apiClient,
                           SignalParserImpl signalParser,
                           CalculationSignalServiceImpl calculationSignalService,
                           TurnParser turnParser) {
        this.board = board;
        this.apiClient = apiClient;
        this.signalParser = signalParser;
        this.calculationSignalService = calculationSignalService;
        this.turnParser = turnParser;
    }

    /**
     * Function that sends a signal to REST API client about start of a new UE call.
     *
     * @author najedzony
     * @author Barbara Moczulska
     */
    private void startCall(UE ue) {
        boolean response = apiClient.postStartUEToCallsManagementSystem(
                signalParser.parseUESignalHashMap(calculationSignalService.calculateSignal(ue), ue.name()));

        if (!response) {
            LOGGER.error("Sending a START signal to REST API failed");
        } else
            LOGGER.info("Sending a START signal to REST API succeeded");
    }

    /**
     * Function that sends a signal to REST API client about move of a UE.
     *
     * @author Barbara Moczulska
     */
    private void moveCall(UE ue) {
        boolean response = apiClient.postMoveUEToCallsManagementSystem(
                signalParser.parseUESignalHashMap(calculationSignalService.calculateSignal(ue), ue.name()));

        if (!response) {
            LOGGER.error("Sending a MOVE signal to REST API failed");
        } else
            LOGGER.info("Sending a MOVE signal to REST API succeeded");
    }

    /**
     * Function that sends a signal to REST API client about end of an ongoing UE call.
     *
     * @author najedzony
     * @author Barbara Moczulska
     */
    private void endCall(UE ue) {

        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("name", ue.name());

        boolean response = apiClient.postEndUEToCallsManagementSystem(node.toString());

        if (!response) {
            LOGGER.error("Sending a END signal to REST API failed");
        } else
            LOGGER.info("Sending a END signal to REST API succeeded");
    }

    private void doAction(UEScenario ueScenario, UEStep ueStep) {
        switch (ueStep.action()) {
            case START -> {
                LOGGER.info("Proceeding START action for an UE.");
                board.addUe(ueScenario.ue(), ueStep.localization());
                startCall(ueScenario.ue());
                LOGGER.info("START action finished.");
            }
            case MOVE -> {
                LOGGER.info("Proceeding MOVE action for an UE.");
                board.updateUe(ueScenario.ue(), ueStep.localization());
                moveCall(ueScenario.ue());
                LOGGER.info("MOVE action finished.");
            }
            case END -> {
                try {
                    LOGGER.info("Proceeding END action for an UE.");
                    board.deleteUe(ueScenario.ue());
                    endCall(ueScenario.ue());
                    LOGGER.info("END action finished.");
                } catch (IllegalArgumentException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public void initializeTurn(int turnNumber) {
        boolean response = apiClient.postStartNewTurnToCallsManagementSystem(
                turnParser.parseTurn(turnNumber));

        if (!response) {
            LOGGER.error("Sending a initialization turn signal to REST API failed");
        } else
            LOGGER.info("Sending a initialization turn signal to REST API succeeded");
    }

    @Override
    public void findAndDoActionsForThisTurn(int TurnNumber) {

        scenario.ueScenarios()
                .forEach(ueScenario -> ueScenario.steps().stream()
                        .filter(ueStep -> ueStep.turn() == TurnNumber
                        ).forEach(ueStep -> doAction(ueScenario, ueStep)));
    }

    @Override
    public void setScenario(ScenarioSchema scenario) {
        this.scenario = scenario;
    }

}
