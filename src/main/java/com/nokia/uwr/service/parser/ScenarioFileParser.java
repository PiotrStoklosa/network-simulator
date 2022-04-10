package com.nokia.uwr.service.parser;

import com.nokia.uwr.scenario.ScenarioSchema;

import java.io.File;

/**
 * @author MiSobecki
 * Interface for classes which parse files into ScenarioSchema instance
 */
public interface ScenarioFileParser {
    /**
     * Parses JSON file into ScenarioSchema
     *
     * @param resource JSON file to parse from
     * @return schema of scenario
     */
    ScenarioSchema parseJSONFile(File resource);

}
