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
    private static final Logger LOGGER = LogManager.getLogger(SignalParserImpl.class);
    private final ObjectMapper mapper;

    @Override
    public String parseUESignalHashMap(Map<BTS, Integer> UeSignalHashMap) {

        if (UeSignalHashMap == null) {
            LOGGER.error("map is null");
            throw new IllegalArgumentException("Map is null");
        }

        String json;
        LOGGER.info("map: " + UeSignalHashMap);

        try {
            json = mapper.writeValueAsString(UeSignalHashMap);

        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException();
        }

        LOGGER.info("Read value from map successfully");
        LOGGER.info("json:" + json);

        return json;
    }
}
