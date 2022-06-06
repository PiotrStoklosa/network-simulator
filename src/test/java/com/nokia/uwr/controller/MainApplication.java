package com.nokia.uwr.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Piotr Stoklosa
 */
@SpringBootTest
public class MainApplication {

    @Autowired
    ApplicationInitializer applicationInitializer;

    @Test
    void testNetworkSimulator() {
        applicationInitializer.startApplication();
    }

}
