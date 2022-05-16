package com.nokia.uwr.service.parser;

import java.util.Map;

/**
 * @author Barbara Moczulska
 */
public interface SignalParser {
    /**
     * Parses UE name and HashMap into JSON
     *
     * @param UeSignalHashMap map signals UE into all BTSs
     * @param ueName          UE name
     * @return JSON
     * @author Barbara Moczulska
     */
    String parseUESignalHashMap(Map<String, Integer> UeSignalHashMap, String ueName);
}
