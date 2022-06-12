package com.nokia.uwr.service;

import com.nokia.uwr.board.Board;
import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.scenario.bts.BTSDescription;
import com.nokia.uwr.scenario.ue.UEScenario;
import com.nokia.uwr.scenario.ue.UEStep;
import com.nokia.uwr.service.parser.ScenarioFileParser;
import com.nokia.uwr.service.turns.TurnHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;

/**
 * @author Piotr Stoklosa
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class BoardServiceImplTest {

    @Autowired
    BoardServiceImpl boardService;

    @Mock
    UEScenario ueScenario1;
    @Mock
    UEScenario ueScenario2;
    @Mock
    UEStep ueStep1;
    @Mock
    UEStep ueStep2;
    @Mock
    UEStep ueStep3;
    @Mock
    UEStep ueStep4;

    @BeforeAll
    static void initialize() {
        Mockito.mockStatic(BoardServiceImpl.class);
    }

    @ParameterizedTest
    @MethodSource("provideListsWithTurns")
    void shouldReturnCorrectMaxNumberOfTurnsWhenProperScenarioIsPresent(int ueStepTurn1, int ueStepTurn2, int ueStepTurn3, int ueStepTurn4) {
        List<UEScenario> scenarios = List.of(ueScenario1, ueScenario2);
        Mockito.lenient().when(ueScenario1.steps()).thenReturn(List.of(ueStep1, ueStep2));
        Mockito.lenient().when(ueScenario2.steps()).thenReturn(List.of(ueStep3, ueStep4));
        Mockito.lenient().when(ueStep1.turn()).thenReturn(ueStepTurn1);
        Mockito.lenient().when(ueStep2.turn()).thenReturn(ueStepTurn2);
        Mockito.lenient().when(ueStep3.turn()).thenReturn(ueStepTurn3);
        Mockito.lenient().when(ueStep4.turn()).thenReturn(ueStepTurn4);
        Assertions.assertEquals(Stream.of(ueStepTurn1, ueStepTurn2, ueStepTurn3, ueStepTurn4).max(Comparator.naturalOrder()).get(), boardService.findMaxTurn(scenarios));
    }

    public static Stream<Arguments> provideListsWithTurns() {
        return Stream.of(
                Arguments.of(7, 4, 7, 14),
                Arguments.of(15, 200, 2, 5),
                Arguments.of(4, 3, 2, 1),
                Arguments.of(1, 2, 3, 4),
                Arguments.of(5, 0, 1, 0)
        );
    }

    @Autowired
    Environment environment;

    @Spy
    static
    TurnHandler turnHandler;
    @Mock
    Environment mockedEnvironment;
    @Mock
    ScenarioFileParser scenarioFileParser;
    @Mock
    Board board;
    @Mock
    ConnectionHandler connectionHandler;
    @InjectMocks
    BoardServiceImpl boardServiceWithMocks;

    @ParameterizedTest
    @MethodSource("provideListsNumberOfInvocationsInSimulation")
    void shouldSimulateTurns(int turns) {
        Mockito.lenient().when(BoardServiceImpl.getMaxTurns()).thenReturn(turns);
        Mockito.lenient().doNothing().when(connectionHandler).initializeCMS(Mockito.any());
        boardServiceWithMocks.simulate(new ScenarioSchema(new ArrayList<>(), new ArrayList<>()));
        Mockito.verify(turnHandler, times(turns)).findAndDoActionsForThisTurn(Mockito.anyInt());
    }

    public static Stream<Arguments> provideListsNumberOfInvocationsInSimulation() {
        return Stream.of(
                Arguments.of(5),
                Arguments.of(1500),
                Arguments.of(15),
                Arguments.of(0),
                Arguments.of(2)
        );
    }

    @Test
    void serviceShouldReturnErrorWhenThereIsNoSpecificFile() {

        String wrongFilePath = "wrong.json";
        Mockito.lenient().doNothing().when(turnHandler).findAndDoActionsForThisTurn(Mockito.anyInt());
        Mockito.lenient().when(mockedEnvironment.getProperty("scenario_path")).thenReturn(wrongFilePath);
        Assertions.assertFalse(boardServiceWithMocks.InitialiseBoardAndManageTurns());
    }

    @Test
    void serviceShouldReturnErrorWhenParsedScenarioSchemaIsNull() {
        String correctFilePath = "scenario_path";
        Mockito.lenient().doNothing().when(turnHandler).findAndDoActionsForThisTurn(Mockito.anyInt());
        Mockito.lenient().when(mockedEnvironment.getProperty("scenario_path")).thenReturn(environment.getProperty(correctFilePath));
        Mockito.lenient().when(scenarioFileParser.parseJSONString(Mockito.any())).thenReturn(null);
        Assertions.assertFalse(boardServiceWithMocks.InitialiseBoardAndManageTurns());
    }

    @Mock
    BTSDescription btsDescription;
    @Mock
    UEScenario ueScenario;
    @Mock
    UEStep ueStep5;

    @Test
    void serviceSuccessfulTest() {
        String correctFilePath = "scenario_path";
        Mockito.lenient().doNothing().when(turnHandler).findAndDoActionsForThisTurn(Mockito.anyInt());
        Mockito.lenient().when(mockedEnvironment.getProperty("scenario_path")).thenReturn(environment.getProperty(correctFilePath));
        Mockito.lenient().when(ueStep5.turn()).thenReturn(5);
        Mockito.lenient().when(ueScenario.steps()).thenReturn(List.of(ueStep5));
        Mockito.lenient().when(scenarioFileParser.parseJSONString(Mockito.any())).thenReturn(new ScenarioSchema(
                List.of(btsDescription),
                List.of(ueScenario)));
        Mockito.lenient().when(board.getBtsLocalizationHashMap()).thenReturn(new HashMap<>());
        Assertions.assertTrue(boardServiceWithMocks.InitialiseBoardAndManageTurns());
    }

}
