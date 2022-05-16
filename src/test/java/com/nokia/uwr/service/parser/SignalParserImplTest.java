package com.nokia.uwr.service.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class SignalParserImplTest {


    SignalParser signalParser;

    @Autowired
    public SignalParserImplTest(SignalParser signalParser) {
        this.signalParser = signalParser;
    }

    @Test
    void parseUESignalHashMap() {

        Map<String, Integer> map = Map.of("1", 1,
                "2",  6,
                "3",  7,
                "4",  3);

        String x = "{\"name\":\"UE3\"," +
                    "\"signals\":" +
                            "{\"4\":3," +
                            "\"3\":7," +
                            "\"2\":6," +
                            "\"1\":1}}";


        Assertions.assertEquals(signalParser.parseUESignalHashMap(map, "UE3"), x);

    }
}
