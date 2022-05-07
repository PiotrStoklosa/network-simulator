package com.nokia.uwr.service.rest;

/**
 * @author Piotr Stoklosa
 */
public interface APIClient {
    boolean postStartUEToCallsManagementSystem(String body);

    boolean postMoveUEToCallsManagementSystem(String body);

    boolean postEndUEToCallsManagementSystem(String body);
}
