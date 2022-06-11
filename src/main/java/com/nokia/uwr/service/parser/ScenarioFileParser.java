package com.nokia.uwr.service.parser;

import com.nokia.uwr.scenario.ScenarioSchema;

/**
 * Interface for classes which parse files into ScenarioSchema instance
 *
 * @author MiSobecki
 */
public interface ScenarioFileParser {
    /**
     * Parses JSON file into ScenarioSchema
     *
     * @param resource JSON file to parse from
     * @return schema of scenario
     * @throws IllegalArgumentException thrown if resource is null
     */
    ScenarioSchema parseJSONString(String resource) throws IllegalArgumentException;

}
