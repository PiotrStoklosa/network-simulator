package com.nokia.uwr.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TurnParserImplTest {

    private final TurnParserImpl turnParser = new TurnParserImpl(new ObjectMapper());

    @Test
    void shouldProperlyParseTurn() {
        // given
        int turnNumber1 = 4;
        int turnNumber2 = 17;

        String expected1 = "{\"turnNumber\":4}";
        String expected2 = "{\"turnNumber\":17}";

        // when
        String result1 = turnParser.parseTurn(turnNumber1);
        String result2 = turnParser.parseTurn(turnNumber2);

        // then
        Assertions.assertEquals(expected1, result1);
        Assertions.assertEquals(expected2, result2);
    }

}