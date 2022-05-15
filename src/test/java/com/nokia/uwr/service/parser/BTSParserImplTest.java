package com.nokia.uwr.service.parser;

import com.nokia.uwr.board.Localization;
import com.nokia.uwr.scenario.bts.BTSDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BTSParserImplTest {

    private final BTSParserImpl btsParserImpl = new BTSParserImpl();

    @Test
    public void shouldProperlyParseBTSDescriptions() {
        // given
        List<BTSDescription> bts = new ArrayList<>(
                List.of(
                        new BTSDescription("BTS1", new Localization(1, 5), 40),
                        new BTSDescription("BTS2", new Localization(20, 30), 38)
                )
        );

        String expected = "[{" +
                "\"name\":" +
                "\"BTS1\"," +
                "\"localization\":" +
                "{\"x\":1,\"y\":5}," +
                "\"signalPower\":40},{" +
                "\"name\":" +
                "\"BTS2\"," +
                "\"localization\":" +
                "{\"x\":20,\"y\":30}," +
                "\"signalPower\":38}]";

        System.out.println();

        // when
        String result = btsParserImpl.parseBTSDescriptions(bts);

        // then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void shouldThrowExceptionWhenNullPassed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> btsParserImpl.parseBTSDescriptions(null));
    }

}