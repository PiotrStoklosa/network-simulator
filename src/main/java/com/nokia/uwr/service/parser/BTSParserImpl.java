package com.nokia.uwr.service.parser;

import com.nokia.uwr.scenario.bts.BTSDescription;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Main implementation
 *
 * @author Piotr Stoklosa
 */
@Service
@RequiredArgsConstructor
public class BTSParserImpl implements BTSParser {

    private static final Logger LOGGER = LogManager.getLogger(BTSParserImpl.class);

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public String parseBTSDescriptions(List<BTSDescription> descriptions) throws IllegalArgumentException {

        if (descriptions == null) throw new IllegalArgumentException("descriptions is null");

        LOGGER.info("Creating JSON from BTS descriptions...");
        String json = new JSONArray(descriptions).toString();
        LOGGER.info("JSON created successfully!");

        return json;
    }
}
