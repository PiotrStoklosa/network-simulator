package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.uwr.scenario.bts.BTSDescription;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Main implementation
 *
 * @author Piotr Stoklosa
 */
@Service
@AllArgsConstructor
public class BTSParserImpl implements BTSParser {

    private static final Logger LOGGER = LogManager.getLogger(BTSParserImpl.class);

    ObjectMapper mapper;

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public String parseBTSDescriptions(List<BTSDescription> descriptions) throws IllegalArgumentException {

        if (descriptions == null) throw new IllegalArgumentException("descriptions is null");

        String json;
        LOGGER.info("Creating JSON from BTS descriptions...");

        try {
            json = mapper.writeValueAsString(descriptions);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        LOGGER.info("JSON created successfully!");
        return json;
    }
}
