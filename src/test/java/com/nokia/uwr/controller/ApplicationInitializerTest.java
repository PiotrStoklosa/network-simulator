package com.nokia.uwr.controller;

import com.nokia.uwr.service.rest.APIClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ApplicationInitializerTest {

    @MockBean
    APIClient apiClient;

    @Autowired
    ApplicationInitializer applicationInitializer;

    @BeforeAll
    public void setUp() {
        Mockito.when(apiClient.postInitializeToCallsManagementSystem(Mockito.anyString())).thenReturn(true);
        Mockito.when(apiClient.postEndUEToCallsManagementSystem(Mockito.anyString())).thenReturn(true);
        Mockito.when(apiClient.postMoveUEToCallsManagementSystem(Mockito.anyString())).thenReturn(true);
        Mockito.when(apiClient.postStartNewTurnToCallsManagementSystem(Mockito.anyString())).thenReturn(true);
        Mockito.when(apiClient.postStartUEToCallsManagementSystem(Mockito.anyString())).thenReturn(true);
        Mockito.when(apiClient.postTerminateToCallsManagementSystem()).thenReturn(true);
    }

    @Test
    void testNetworkSimulator() {
        applicationInitializer.startApplication();
    }

}
