package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.uwr.scenario.ScenarioSchema;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author MiSobecki
 * Class to parse files into ScenarioSchema instance
 */
@Service
@RequiredArgsConstructor
public class ScenarioFileParserImpl implements ScenarioFileParser {
    private final ObjectMapper mapper;
    private static final Logger LOGGER = LogManager.getLogger(ScenarioFileParserImpl.class);

    @Override
    public ScenarioSchema parseJSONFile(File resource) {
        try {
            LOGGER.info("Read value from JSON file");

            ScenarioSchema scenarioSchema = mapper.readValue(resource, ScenarioSchema.class);

            LOGGER.info("Read value from JSON file successfully");

            return scenarioSchema;
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace());
            return null;
        }
    }
}