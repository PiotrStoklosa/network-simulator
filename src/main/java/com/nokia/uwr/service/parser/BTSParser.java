package com.nokia.uwr.service.parser;

import com.nokia.uwr.scenario.bts.BTSDescription;

import java.util.List;

/**
 * Main implementation
 *
 * @author Piotr Stoklosa
 */
public interface BTSParser {

    /**
     * Parses list of BTS descriptions into JSON
     *
     * @param descriptions BTS description list
     * @return created JSON from descriptions
     * @throws IllegalArgumentException when descriptions parameter is null
     */
    String parseBTSDescriptions(List<BTSDescription> descriptions) throws IllegalArgumentException;

}
