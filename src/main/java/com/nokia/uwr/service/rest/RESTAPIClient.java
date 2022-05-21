package com.nokia.uwr.service.rest;

import com.nokia.uwr.scenario.ue.UEAction;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Main implementation of APIClient
 *
 * @author Piotr Stoklosa
 * @see APIClient
 */
@Component
public class RESTAPIClient implements APIClient {

    private static final Logger LOGGER = LogManager.getLogger(RESTAPIClient.class);
    private final String callsManagementSystemPath;
    private static final String ueEndpoint = "/api/calls/ue/";
    private static final String initializerEndpoint = "/api/calls/initializer/";
    private static final String terminateEndpoint = "/api/calls/terminator/";
    private static final String newTurnEndpoint = "/api/calls/turn/";
    private static final String EMPTY = "{}";

    @Autowired
    public RESTAPIClient(Environment environment) {
        callsManagementSystemPath = environment.getProperty("calls_management_system_path");
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postStartNewTurnToCallsManagementSystem(String body) {
        return postToCallsManagementSystem(body, newTurnEndpoint);
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postStartUEToCallsManagementSystem(String body) {
        return postUEActionToCallsManagementSystem(body, UEAction.START);
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postMoveUEToCallsManagementSystem(String body) {
        return postUEActionToCallsManagementSystem(body, UEAction.MOVE);
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postEndUEToCallsManagementSystem(String body) {
        return postUEActionToCallsManagementSystem(body, UEAction.END);
    }

    private boolean postUEActionToCallsManagementSystem(String body, UEAction ueAction) {

        return postToCallsManagementSystem(body, callsManagementSystemPath + ueEndpoint + ueAction.name());

    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postInitializeToCallsManagementSystem(String body) {
        return postToCallsManagementSystem(body, initializerEndpoint);
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postTerminateToCallsManagementSystem() {
        return postToCallsManagementSystem(EMPTY, terminateEndpoint);
    }

    private boolean postToCallsManagementSystem(String body, String endpoint) {

        LOGGER.info("Proceeding post request to calls-management-system");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response;
        try {
            response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exc) {
            LOGGER.error("Error occurred during post method to calls-management-system");
            LOGGER.error(exc.getMessage());
            return false;
        }
        if (response.statusCode() == HttpStatus.SC_OK) {
            LOGGER.info("Post UE method ended successfully!");
            return true;
        } else {
            LOGGER.error("Post UE method ended with error, status code: " + response.statusCode());
            return false;
        }

    }

}
