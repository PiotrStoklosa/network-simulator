package com.nokia.uwr.service.rest;

import com.nokia.uwr.scenario.ue.UEAction;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Main implementation of APIClient
 *
 * @author Piotr Stoklosa
 * @see APIClient
 */
@Component
public class RESTAPIClient implements APIClient {

    private static final Logger LOGGER = LogManager.getLogger(RESTAPIClient.class);
    private final String CALLS_MANAGEMENT_SYSTEM_PATH;
    private static final String UE_ENDPOINT = "/api/calls/";
    private static final String INITIALIZER_ENDPOINT = "/api/initializer/";
    private static final String TERMINATE_ENDPOINT = "/api/terminator/";
    private static final String NEW_TURN_ENDPOINT = "/api/turns/";
    private static final String EMPTY = "{}";

    @Autowired
    public RESTAPIClient(Environment environment) {
        CALLS_MANAGEMENT_SYSTEM_PATH = environment.getProperty("calls_management_system_path");
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postStartNewTurnToCallsManagementSystem(String body) {
        return postToCallsManagementSystem(body, CALLS_MANAGEMENT_SYSTEM_PATH + NEW_TURN_ENDPOINT);
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

        return postToCallsManagementSystem(body, CALLS_MANAGEMENT_SYSTEM_PATH + UE_ENDPOINT + ueAction.name());

    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postInitializeToCallsManagementSystem(String body) {
        return postToCallsManagementSystem(body, CALLS_MANAGEMENT_SYSTEM_PATH + INITIALIZER_ENDPOINT);
    }

    /**
     * Main implementation
     *
     * @author Piotr Stoklosa
     */
    @Override
    public boolean postTerminateToCallsManagementSystem() {
        return postToCallsManagementSystem(EMPTY, CALLS_MANAGEMENT_SYSTEM_PATH + TERMINATE_ENDPOINT);
    }

    private boolean postToCallsManagementSystem(String body, String endpoint) {
        endpoint = endpoint.toLowerCase();
        LOGGER.info("Proceeding post request to calls-management-system");

        StringEntity entity = new StringEntity(body,
                ContentType.APPLICATION_JSON);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(endpoint);
        request.setEntity(entity);
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException exc) {
            LOGGER.error("Error occurred during post method to calls-management-system");
            LOGGER.error(exc.getMessage());
            return false;
        }
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            LOGGER.info("Post UE method ended successfully!");
            return true;
        } else {
            LOGGER.error("Post UE method ended with error, status code: " + response.getStatusLine().getStatusCode());
            return false;
        }
    }

}
