package com.nokia.uwr.service.parser;

import com.nokia.uwr.model.BTS;

import java.util.Map;

/**
 * @author Barbara Moczulska
 */
public interface SignalParser {
    /**
     * Parses HashMap into JSON
     *
     * @param UeSignalHashMap map signals UE into all BTSs
     * @return JSON
     * @author Barbara Moczulska
     */
    String parseUESignalHashMap(Map<BTS, Integer> UeSignalHashMap);
}
