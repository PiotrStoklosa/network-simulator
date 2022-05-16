package com.nokia.uwr.service;

import com.nokia.uwr.board.Board;
import com.nokia.uwr.board.Localization;
import com.nokia.uwr.model.BTS;
import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.scenario.ue.UEScenario;
import com.nokia.uwr.scenario.ue.UEStep;
import com.nokia.uwr.service.parser.ScenarioFileParser;
import com.nokia.uwr.service.turns.TurnHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Implementation of Board Service
 *
 * @author Piotr Stoklosa
 * @see BoardService
 */
@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger LOGGER = LogManager.getLogger(BoardServiceImpl.class);

    private final ScenarioFileParser scenarioFileParser;
    private final Environment env;
    private final Board board;
    private final TurnHandler turnHandler;
    private final ConnectionHandler connectionHandler;

    private static int MAX_TURNS;

    @Autowired
    public BoardServiceImpl(ScenarioFileParser scenarioFileParser,
                            Environment env,
                            Board board,
                            TurnHandler turnHandler,
                            ConnectionHandler connectionHandler) {
        this.scenarioFileParser = scenarioFileParser;
        this.env = env;
        this.board = board;
        this.turnHandler = turnHandler;
        this.connectionHandler = connectionHandler;
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     * @see BoardService#InitialiseBoardAndManageTurns()
     */
    @Override
    public boolean InitialiseBoardAndManageTurns() {
        File scenario;
        String path = env.getProperty("scenario_path");
        try {
            scenario = ResourceUtils.getFile(Objects.requireNonNull(
                    this.getClass().getResource(Objects.requireNonNull(path))));
        } catch (Exception e) {
            LOGGER.error("Error occurred during obtaining a file! Path: " + path + " , exception: " + e.getMessage());
            return false;
        }
        ScenarioSchema scenarioSchema = scenarioFileParser.parseJSONFile(scenario);
        if (scenarioSchema == null) {
            return false;
        }
        LOGGER.info("Scenario schema loaded successfully, schema: " + scenarioSchema);

        addBTS(scenarioSchema);
        MAX_TURNS = findMaxTurn(scenarioSchema.ueScenarios());
        simulate(scenarioSchema);

        return true;
    }

    /**
     * findMaxTurn finds the number of turns that have to be managed.
     *
     * @param ueScenarios list of scenarios that store details about UE's turns
     * @return Number of turns that have to be managed
     * @throws RuntimeException when method do not find number of turns
     * @author Piotr Stoklosa
     */
    int findMaxTurn(List<UEScenario> ueScenarios) {
        Optional<Integer> maxTurn = ueScenarios.stream()
                .map(UEScenario::steps)
                .flatMap(Collection::stream)
                .map(UEStep::turn)
                .max(Comparator.naturalOrder());
        if (maxTurn.isPresent()) {
            LOGGER.info("Number of turns: " + maxTurn.get());
            return maxTurn.get();
        } else {
            LOGGER.error("Did not found number of turns!");
            throw new RuntimeException("Did not found number of turns!");
        }
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     * @see BoardService#simulate(ScenarioSchema)
     */
    @Override
    public void simulate(ScenarioSchema scenarioSchema) {

        connectionHandler.initializeCall(scenarioSchema.btsDescriptions());
        turnHandler.setScenario(scenarioSchema);
        IntStream.rangeClosed(1, getMaxTurns()).forEach(turn -> {
                    LOGGER.info("Turn  " + turn + "/" + getMaxTurns());
                    turnHandler.findAndDoActionsForThisTurn(turn);
                    LOGGER.info("Turn ended successfully!");
                }
        );
        connectionHandler.terminateCall();

    }

    static int getMaxTurns() {
        return MAX_TURNS;
    }

    /**
     * addBTS add BTS' to BTS' map in board.
     *
     * @author Piotr Stoklosa
     * @see Board
     */
    private void addBTS(ScenarioSchema scenarioSchema) {
        Map<BTS, Localization> BtsLocalizationHashMap = board.getBtsLocalizationHashMap();
        scenarioSchema.btsDescriptions().forEach(btsDescription -> BtsLocalizationHashMap.put(
                new BTS(btsDescription.name(), btsDescription.signalPower()),
                btsDescription.localization()));
    }
}
