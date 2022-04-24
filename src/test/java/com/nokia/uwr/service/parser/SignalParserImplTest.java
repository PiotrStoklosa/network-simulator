package com.nokia.uwr.service.parser;

import com.nokia.uwr.board.Board;
import com.nokia.uwr.model.BTS;
import com.nokia.uwr.service.CalculationSignalServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignalParserImplTest {


    SignalParser signalParser;
    @Autowired
    public SignalParserImplTest(SignalParser signalParser) {
        this.signalParser = signalParser;
    }

    @Test
    void parseUESignalHashMap() {

        Map<BTS, Integer> map = new HashMap<>();
        map.put(new BTS("1", 1), 1);
        map.put(new BTS("2", 2), 6);
        map.put(new BTS("3", 5), 7);
        map.put(new BTS("4", 7), 3);

        String x = "{\"BTS[name=1, signalPower=1]\":1,\"BTS[name=2, signalPower=2]\":6,\"BTS[name=3, signalPower=5]\":7,\"BTS[name=4, signalPower=7]\":3}";

        Assertions.assertEquals(signalParser.parseUESignalHashMap(map), x);

    }
}