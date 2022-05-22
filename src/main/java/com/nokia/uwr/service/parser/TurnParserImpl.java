package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Main implementation
 *
 * @author Piotr Stoklosa
 */
@Service
@AllArgsConstructor
public class TurnParserImpl implements TurnParser {
    private static final Logger LOGGER = LogManager.getLogger(TurnParserImpl.class);

    private final ObjectMapper mapper;


    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public String parseTurn(int turnNumber) {

        String json;
        LOGGER.info("Parsing turn " + turnNumber);
        Map<String, Integer> turnJson = new HashMap<>();
        turnJson.put("turnNumber", turnNumber);

        try {

            json = mapper.writeValueAsString(turnJson);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        LOGGER.info("JSON created successfully!");
        return json;
    }
}
