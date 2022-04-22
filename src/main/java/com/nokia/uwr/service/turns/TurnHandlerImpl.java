package com.nokia.uwr.service.turns;

import com.nokia.uwr.board.Board;
import com.nokia.uwr.model.UE;
import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.scenario.ue.UEAction;
import com.nokia.uwr.scenario.ue.UEScenario;
import com.nokia.uwr.scenario.ue.UEStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Class that performs actions for certain turns.
 *
 * @author najedzony
 */
@Service
public class TurnHandlerImpl implements TurnHandler {

    private static final Logger LOGGER = LogManager.getLogger(Board.class);

    private final Board board;
    private ScenarioSchema scenario;

    public TurnHandlerImpl(Board board) {
        this.board = board;
    }

    /**
     * Function that sends a signal to CALLSYS about start of a new call.
     *
     * @author najedzony
     */
    private void StartCall() {
        /* TODO */
    }

    /**
     * Function that sends a signal to CALLSYS about end of an ongoing call.
     *
     * @author najedzony
     */
    private void EndCall() {
        /* TODO */
    }

    @Override
    public void FindAndDoActionsForThisTurn(int TurnNumber) {
        for (UEScenario ueScenario : scenario.ueScenarios()) {
            for (UEStep ueStep : ueScenario.steps()) {
                if (ueStep.turn() == TurnNumber) {
                    if (ueStep.action() == UEAction.START) {
                        LOGGER.info("Proceeding START action for an UE.");
                        board.addUe(new UE(ueScenario.name()), ueStep.localization());
                        StartCall();
                        LOGGER.info("START action finished.");
                    } else if (ueStep.action() == UEAction.MOVE) {
                        LOGGER.info("Proceeding MOVE action for an UE.");
                        board.updateUe(new UE(ueScenario.name()), ueStep.localization());
                        LOGGER.info("MOVE action finished.");
                    } else if (ueStep.action() == UEAction.END) {
                        try {
                            LOGGER.info("Proceeding END action for an UE.");
                            board.deleteUe(new UE(ueScenario.name()));
                            EndCall();
                            LOGGER.info("END action finished.");
                        } catch (IllegalArgumentException e) {
                            LOGGER.info(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setScenario(ScenarioSchema scenario) {
        this.scenario = scenario;
    }
}
