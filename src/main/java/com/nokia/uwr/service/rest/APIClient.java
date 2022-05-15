package com.nokia.uwr.service.rest;

/**
 * APIClient provides methods to communicate with calls-management-system.
 *
 * @author Piotr Stoklosa
 */
public interface APIClient {
    /**
     * Send request to initialize UE
     *
     * @param body request content
     * @return true if request proceed successfully, otherwise false
     * @author Piotr Stoklosa
     */
    boolean postStartUEToCallsManagementSystem(String body);

    /**
     * Send request to move UE
     *
     * @param body request content
     * @return true if request proceed successfully, otherwise false
     * @author Piotr Stoklosa
     */
    boolean postMoveUEToCallsManagementSystem(String body);

    /**
     * Send request to terminate UE
     *
     * @param body request content
     * @return true if request proceed successfully, otherwise false
     * @author Piotr Stoklosa
     */
    boolean postEndUEToCallsManagementSystem(String body);

    /**
     * Send request to initialize connection
     *
     * @param body request content
     * @return true if request proceed successfully, otherwise false
     * @author Piotr Stoklosa
     */
    boolean postInitializeToCallsManagementSystem(String body);

}
