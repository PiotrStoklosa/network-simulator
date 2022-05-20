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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

@SpringBootTest
class TurnHandlerImplTest {

    private Board board;
    private TurnHandlerImpl turnHandler;
    private UE ue1;
    private UE ue2;


    public void setup() {
        board = new Board();
        APIClient apiClient = mock(APIClient.class);
        SignalParserImpl signalParser = mock(SignalParserImpl.class);
        CalculationSignalServiceImpl calculationSignalService = mock(CalculationSignalServiceImpl.class);
        turnHandler = new TurnHandlerImpl(board, apiClient, signalParser, calculationSignalService);

        List<BTSDescription> bts = new ArrayList<>(
                List.of(
                        new BTSDescription("BTS1", new Localization(1, 5), 40),
                        new BTSDescription("BTS2", new Localization(20, 30), 38)
                )
        );

        List<UEScenario> ues = new ArrayList<>(
                List.of(
                        new UEScenario(new UE("UE1"), new ArrayList<>(
                                List.of(new UEStep(10, new Localization(10, 11), UEAction.START),
                                        new UEStep(12, new Localization(10, 12), UEAction.MOVE),
                                        new UEStep(15, new Localization(10, 12), UEAction.END)))),
                        new UEScenario(new UE("UE2"), new ArrayList<>(
                                List.of(new UEStep(10, new Localization(20, 18), UEAction.START),
                                        new UEStep(12, new Localization(22, 18), UEAction.MOVE),
                                        new UEStep(15, new Localization(24, 16), UEAction.END))))
                )
        );

        ScenarioSchema schema = new ScenarioSchema(bts, ues);
        turnHandler.setScenario(schema);
        ue1 = new UE("UE1");
        ue2 = new UE("UE2");
    }

    @Test
    public void EverythingShouldBeOk() {
        setup();

        int ok = 0;
        turnHandler.findAndDoActionsForThisTurn(10);
        Map<UE, Localization> ueLocalizationHashMap = board.getUeLocalizationHashMap();
        if (ueLocalizationHashMap.containsKey(ue1) && ueLocalizationHashMap.get(ue1).equals(new Localization(10, 11))) {
            ok++;
        }
        if (ueLocalizationHashMap.containsKey(ue2) && ueLocalizationHashMap.get(ue2).equals(new Localization(20, 18))) {
            ok++;
        }
        Assertions.assertEquals(2, ok);

        ok = 0;
        turnHandler.findAndDoActionsForThisTurn(12);
        ueLocalizationHashMap = board.getUeLocalizationHashMap();
        if (ueLocalizationHashMap.containsKey(ue1) && ueLocalizationHashMap.get(ue1).equals(new Localization(10, 12))) {
            ok++;
        }
        if (ueLocalizationHashMap.containsKey(ue2) && ueLocalizationHashMap.get(ue2).equals(new Localization(22, 18))) {
            ok++;
        }
        Assertions.assertEquals(2, ok);

        ok = 0;
        turnHandler.findAndDoActionsForThisTurn(15);
        ueLocalizationHashMap = board.getUeLocalizationHashMap();
        if (!ueLocalizationHashMap.containsKey(ue1)) {
            ok++;
        }
        if (!ueLocalizationHashMap.containsKey(ue2)) {
            ok++;
        }
        Assertions.assertEquals(2, ok);
    }
}