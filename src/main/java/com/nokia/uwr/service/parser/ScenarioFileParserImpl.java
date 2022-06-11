package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.uwr.scenario.ScenarioSchema;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Class to parse files into ScenarioSchema instance
 *
 * @author MiSobecki
 * @author Piotr Stoklosa
 */
@Service
@RequiredArgsConstructor
public class ScenarioFileParserImpl implements ScenarioFileParser {
    private final ObjectMapper mapper;
    private static final Logger LOGGER = LogManager.getLogger(ScenarioFileParserImpl.class);

    @Override
    public ScenarioSchema parseJSONString(String resource) throws IllegalArgumentException {
        if (resource == null) throw new IllegalArgumentException("Resource is null");

        try {
            LOGGER.info("Read value from JSON String: " + resource);

            ScenarioSchema scenarioSchema = mapper.readValue(resource, ScenarioSchema.class);

            LOGGER.info("Read value from JSON String successfully");

            return scenarioSchema;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}