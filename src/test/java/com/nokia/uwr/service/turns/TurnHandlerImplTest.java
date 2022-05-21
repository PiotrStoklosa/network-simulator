package com.nokia.uwr.service.turns;

import com.nokia.uwr.board.Board;
import com.nokia.uwr.board.Localization;
import com.nokia.uwr.model.UE;
import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.scenario.bts.BTSDescription;
import com.nokia.uwr.scenario.ue.UEAction;
import com.nokia.uwr.scenario.ue.UEScenario;
import com.nokia.uwr.scenario.ue.UEStep;
import com.nokia.uwr.service.CalculationSignalServiceImpl;
import com.nokia.uwr.service.parser.SignalParserImpl;
import com.nokia.uwr.service.rest.APIClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TurnHandlerImplTest {

    private Board board;
    private TurnHandlerImpl turnHandler;
    private UE ue1;
    private UE ue2;
    private Localization localizationForAddingUe1;
    private Localization localizationForAddingUe2;
    private Localization localizationForUpdatingUe1;
    private Localization localizationForUpdatingUe2;

    @BeforeEach
    public void setup() {
        board = mock(Board.class);
        APIClient apiClient = mock(APIClient.class);
        SignalParserImpl signalParser = mock(SignalParserImpl.class);
        CalculationSignalServiceImpl calculationSignalService = mock(CalculationSignalServiceImpl.class);
        turnHandler = new TurnHandlerImpl(board, apiClient, signalParser, calculationSignalService);
        localizationForAddingUe1 = new Localization(10, 11);
        localizationForAddingUe2 = new Localization(20, 18);
        localizationForUpdatingUe1 = new Localization(10, 12);
        localizationForUpdatingUe2 = new Localization(22, 18);


        List<BTSDescription> bts = new ArrayList<>(
                List.of(
                        new BTSDescription(
                                "BTS1",
                                new Localization(1, 5),
                                40),
                        new BTSDescription(
                                "BTS2",
                                new Localization(20, 30),
                                38)
                )
        );

        List<UEScenario> ues = new ArrayList<>(
                List.of(
                        new UEScenario(new UE("UE1"), new ArrayList<>(
                                List.of(new UEStep(
                                                10,
                                                localizationForAddingUe1,
                                                UEAction.START),
                                        new UEStep(
                                                12,
                                                localizationForUpdatingUe1,
                                                UEAction.MOVE),
                                        new UEStep(
                                                15,
                                                localizationForUpdatingUe1,
                                                UEAction.END)))),
                        new UEScenario(new UE("UE2"), new ArrayList<>(
                                List.of(new UEStep(
                                                10,
                                                localizationForAddingUe2,
                                                UEAction.START),
                                        new UEStep(
                                                12,
                                                localizationForUpdatingUe2,
                                                UEAction.MOVE),
                                        new UEStep(
                                                15,
                                                localizationForUpdatingUe2,
                                                UEAction.END))))
                )
        );

        ScenarioSchema schema = new ScenarioSchema(bts, ues);
        turnHandler.setScenario(schema);
        ue1 = new UE("UE1");
        ue2 = new UE("UE2");
    }

    @Test
    public void shouldUpdateBoardCorrectly() {

        //given
        List<Integer> turnNumbers = new ArrayList<>(List.of(10, 12, 15));

        //when
        turnNumbers.forEach(i -> turnHandler.findAndDoActionsForThisTurn(i));

        //then
        verify(board).addUe(ue1, localizationForAddingUe1);
        verify(board).addUe(ue2, localizationForAddingUe2);

        verify(board).updateUe(ue1, localizationForUpdatingUe1);
        verify(board).updateUe(ue2, localizationForUpdatingUe2);

        verify(board).deleteUe(ue1);
        verify(board).deleteUe(ue2);

        verify(board, times(2)).addUe(any(UE.class), any(Localization.class));
        verify(board, times(2)).updateUe(any(UE.class), any(Localization.class));
        verify(board, times(2)).deleteUe(any(UE.class));

    }
}