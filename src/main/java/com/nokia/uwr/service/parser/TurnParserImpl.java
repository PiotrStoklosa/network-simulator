package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

        try {
            json = mapper.writeValueAsString(turnNumber);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        LOGGER.info("JSON created successfully!");
        return json;
    }
}
