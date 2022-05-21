package com.nokia.uwr.service;

import com.nokia.uwr.scenario.bts.BTSDescription;

import java.util.List;

/**
 * Handler for starting and terminating connection between network-simulator and calls-management-system
 *
 * @author Piotr Stoklosa
 */
public interface ConnectionHandler {

    /**
     * Create connection between network-simulator and calls-management-system
     *
     * @param descriptionList descriptionList needed to properly initialize board on calls-management-system side.
     * @author Piotr Stoklosa
     */
    void initializeCMS(List<BTSDescription> descriptionList);

    /**
     * Terminate connection between network-simulator and calls-management-system
     *
     * @author Piotr Stoklosa
     */
    void terminateCMS();

}
