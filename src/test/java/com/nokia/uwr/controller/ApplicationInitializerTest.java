package com.nokia.uwr.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ApplicationInitializerTest {

    @Autowired
    ApplicationInitializer applicationInitializer;

    @Test
    void testNetworkSimulator(){
        applicationInitializer.startApplication();
    }

}