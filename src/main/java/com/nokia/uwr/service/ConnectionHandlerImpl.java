package com.nokia.uwr.service;

import com.nokia.uwr.scenario.bts.BTSDescription;
import com.nokia.uwr.service.parser.BTSParser;
import com.nokia.uwr.service.rest.APIClient;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Main implementation
 *
 * @author Piotr Stoklosa
 */
@Service
@RequiredArgsConstructor
public class ConnectionHandlerImpl implements ConnectionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionHandlerImpl.class);

    private final APIClient apiClient;
    private final BTSParser btsParser;

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    public void initializeCMS(List<BTSDescription> descriptionList) {
        boolean response = apiClient.postInitializeToCallsManagementSystem(
                btsParser.parseBTSDescriptions(descriptionList));

        if (!response) {
            LOGGER.error("Sending a initialization signal to REST API failed");
        } else
            LOGGER.info("Sending a initialization signal to REST API succeeded");
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    public void terminateCMS() {
        boolean response = apiClient.postTerminateToCallsManagementSystem();

        if (!response) {
            LOGGER.error("Termination signal to REST API failed");
        } else
            LOGGER.info("Termination signal to REST API succeeded");
    }


}
