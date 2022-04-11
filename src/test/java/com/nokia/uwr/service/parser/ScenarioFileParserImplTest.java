package com.nokia.uwr.service.parser;

import com.nokia.uwr.board.Localization;
import com.nokia.uwr.scenario.ScenarioSchema;
import com.nokia.uwr.scenario.bts.BTSDescription;
import com.nokia.uwr.scenario.ue.UEScenario;
import com.nokia.uwr.scenario.ue.UEStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class ScenarioFileParserImplTest {

    @Autowired
    private ScenarioFileParser scenarioFileParser;

    private static final Logger LOGGER = LogManager.getLogger(ScenarioFileParserImplTest.class);

    @Test
    public void shouldReturnCorrectScenarioSchemaInstanceWhileGivenCorrectFileFormat() {
        // given
        List<BTSDescription> bts = new ArrayList<>();
        bts.add(new BTSDescription("BTS1", new Localization(1, 5), 40));
        bts.add(new BTSDescription("BTS2", new Localization(20, 30), 38));

        List<UEScenario> ues = new ArrayList<>();
        ues.add(new UEScenario("UE1", new ArrayList<>(
                List.of(new UEStep(10, new Localization(10, 11), "start"),
                        new UEStep(12, new Localization(10, 12), "move"),
                        new UEStep(15, new Localization(10, 12), "end")))));
        ues.add(new UEScenario("UE2", new ArrayList<>(
                List.of(new UEStep(5, new Localization(20, 18), "start"),
                        new UEStep(6, new Localization(22, 18), "move"),
                        new UEStep(7, new Localization(24, 18), "move"),
                        new UEStep(8, new Localization(24, 16), "end")))));

        ScenarioSchema expected = new ScenarioSchema(bts, ues);

        // when
        ScenarioSchema returned;

        try {
            File resource = ResourceUtils.getFile(Objects.requireNonNull(
                    this.getClass().getResource("/correctFile.json")));

            returned = scenarioFileParser.parseJSONFile(resource);
        } catch (FileNotFoundException e) {
            returned = new ScenarioSchema(new ArrayList<>(), new ArrayList<>());
        }

        // then
        Assertions.assertEquals(expected, returned);
    }

    @Test
    public void shouldReturnNullWhileGivenIncorrectFileFormat() {
        // when
        ScenarioSchema returned;

        try {
            File resource = ResourceUtils.getFile(Objects.requireNonNull(
                    this.getClass().getResource("/incorrectFile.json")));

            returned = scenarioFileParser.parseJSONFile(resource);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            returned = null;
        }

        // then
        Assertions.assertNull(returned);
    }

}