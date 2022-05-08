package com.nokia.uwr.service.parser;

import com.nokia.uwr.model.BTS;
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

        Map<BTS, Integer> map = Map.of(new BTS("1", 1), 1,
                new BTS("2", 2), 6,
                new BTS("3", 5), 7,
                new BTS("4", 7), 3);

        String x = "{\"name\":\"UE3\"," +
                    "\"signals\":{\"BTS[name=1, signalPower=1]\":1," +
                                "\"BTS[name=2, signalPower=2]\":6," +
                                "\"BTS[name=3, signalPower=5]\":7," +
                                "\"BTS[name=4, signalPower=7]\":3}}";

        Assertions.assertEquals(signalParser.parseUESignalHashMap(map,"UE3"), x);

    }
}
