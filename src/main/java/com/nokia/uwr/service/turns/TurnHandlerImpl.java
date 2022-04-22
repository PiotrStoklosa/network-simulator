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

    private static final Logger LOGGER = LogManager.getLogger(TurnHandlerImpl.class);

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

    private void DoAction(UEScenario ueScenario, UEStep ueStep){
        switch(ueStep.action()){
            case START:
                LOGGER.info("Proceeding START action for an UE.");
                board.addUe(new UE(ueScenario.name()), ueStep.localization());
                StartCall();
                LOGGER.info("START action finished.");
                break;
            case MOVE:
                LOGGER.info("Proceeding MOVE action for an UE.");
                board.updateUe(new UE(ueScenario.name()), ueStep.localization());
                LOGGER.info("MOVE action finished.");
                break;
            case END:
                try {
                    LOGGER.info("Proceeding END action for an UE.");
                    board.deleteUe(new UE(ueScenario.name()));
                    EndCall();
                    LOGGER.info("END action finished.");
                } catch (IllegalArgumentException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
        }
    }

    @Override
    public void FindAndDoActionsForThisTurn(int TurnNumber) {
        for (UEScenario ueScenario : scenario.ueScenarios()) {
                UEStep ueStep = ueScenario.steps().get(0);
                if(ueStep.turn() == TurnNumber){
                    DoAction(ueScenario, ueStep);
                    ueScenario.steps().remove(0);
                }
        }
    }

    @Override
    public void setScenario(ScenarioSchema scenario) {
        this.scenario = scenario;
    }
}
