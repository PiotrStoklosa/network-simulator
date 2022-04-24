package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.uwr.model.BTS;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Class to parse HashMap into JSON
 *
 * @author Barbara Moczulska
 */
@Service
@RequiredArgsConstructor
public class SignalParserImpl implements SignalParser {
    private final ObjectMapper mapper;
    private static final Logger LOGGER = LogManager.getLogger(SignalParserImpl.class);

    @Override
    public String parseUESignalHashMap(Map<BTS, Integer> UeSignalHashMap) {

        if (UeSignalHashMap == null)
            throw new IllegalArgumentException("Map is null");

        String json;

        try {
            LOGGER.info("Read value from map: " + UeSignalHashMap);

            json = mapper.writeValueAsString(UeSignalHashMap);

            LOGGER.info("Read value from map successfully");

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException();
        }

        return json;
    }
}

