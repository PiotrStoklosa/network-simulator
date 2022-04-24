package com.nokia.uwr.service;

import com.nokia.uwr.board.Board;
import com.nokia.uwr.board.Localization;
import com.nokia.uwr.model.BTS;
import com.nokia.uwr.model.UE;
import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.service.parser.ScenarioFileParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Piotr Stoklosa
 */
@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger LOGGER = LogManager.getLogger(BoardServiceImpl.class);

    private static int turnCounter;

    ScenarioFileParser scenarioFileParser;
    Board board;
    private final Environment env;

    @Autowired
    public BoardServiceImpl(ScenarioFileParser scenarioFileParser, Environment env, Board board) {
        this.scenarioFileParser = scenarioFileParser;
        this.env = env;
        this.board = board;
    }

    @Override
    public boolean boardInitialize() {
        File scenario;
        String path = env.getProperty("scenario_path");
        try {
            scenario = ResourceUtils.getFile(Objects.requireNonNull(
                    this.getClass().getResource(Objects.requireNonNull(path))));
        } catch (Exception e) {
            LOGGER.error("Error occurred during obtaining file! Path: " + path + " , exception: " + e.getMessage());
            return false;
        }
        ScenarioSchema scenarioSchema = scenarioFileParser.parseJSONFile(scenario);
        if (scenarioSchema == null) {
            return false;
        }
        LOGGER.info("Scenario schema loaded successfully, schema: " + scenarioSchema);

        addBTS(scenarioSchema);

        return true;
    }

    private void addBTS(ScenarioSchema scenarioSchema) {
        Map<BTS, Localization> BtsLocalizationHashMap = board.getBtsLocalizationHashMap();
        scenarioSchema.btsDescriptions().forEach(btsDescription -> BtsLocalizationHashMap.put(
                new BTS(btsDescription.name(), btsDescription.signalPower()),
                btsDescription.localization()));
    }
}
