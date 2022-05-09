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
 * @author Piotr Stoklosa
 */
@Component
public class RESTAPIClient implements APIClient {

    private static final Logger LOGGER = LogManager.getLogger(RESTAPIClient.class);
    private final String callsManagementSystemPath;
    private static final String ueEndpoint = "/api/ue/";

    @Autowired
    public RESTAPIClient(Environment environment) {
        callsManagementSystemPath = environment.getProperty("calls_management_system_path");
    }

    public boolean postStartUEToCallsManagementSystem(String body) {
        return postToCallsManagementSystem(body, UEAction.START);
    }

    public boolean postMoveUEToCallsManagementSystem(String body) {
        return postToCallsManagementSystem(body, UEAction.MOVE);
    }

    public boolean postEndUEToCallsManagementSystem(String body) {
        return postToCallsManagementSystem(body, UEAction.END);
    }

    private boolean postToCallsManagementSystem(String body, UEAction ueAction) {

        LOGGER.info("Proceeding post request to ");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(callsManagementSystemPath + ueEndpoint + ueAction.name()))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response;
        try {
            response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exc) {
            LOGGER.error("Error occurred during post method to calls-management-system");
            return false;
        }
        if (response.statusCode() == HttpStatus.SC_OK) {
            LOGGER.info("Post UE method ended successfully!");
            return true;
        } else {
            LOGGER.error("Post UE method ended with error, status code: !" + response.statusCode());
            return false;
        }

    }

}
