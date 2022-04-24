package com.nokia.uwr.service;

import com.nokia.uwr.controller.ApplicationInitializer;

/**
 * Central service that manage turns
 *
 * @author Piotr Stoklosa
 * @see ApplicationInitializer#startApplication
 */
public interface BoardService {

    boolean boardInitialize();

}
