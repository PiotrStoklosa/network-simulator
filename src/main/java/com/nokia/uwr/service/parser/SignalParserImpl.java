package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nokia.uwr.model.BTS;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Class to parse UE name and HashMap into JSON
 *
 * @author Barbara Moczulska
 */
@Service
@RequiredArgsConstructor
public class SignalParserImpl implements SignalParser {
    private static final Logger LOGGER = LogManager.getLogger(SignalParserImpl.class);
    private final ObjectMapper mapper;

    @Override
    public String parseUESignalHashMap(Map<BTS, Integer> UeSignalHashMap, String ueName) {

        if (UeSignalHashMap == null) {
            LOGGER.error("map is null");
            throw new IllegalArgumentException("Map is null");
        }

        LOGGER.info("map: " + UeSignalHashMap);

        String json = "";

        try {
            ObjectNode node = mapper.createObjectNode();
            node.put("name", ueName);
            node.set("signals", mapper.valueToTree(UeSignalHashMap));

            LOGGER.info("Read value from map successfully");

            json = node.toString();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        LOGGER.info("json:" + json);
        return json;
    }
}
