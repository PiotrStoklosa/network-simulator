package com.nokia.uwr.controller;

import com.nokia.uwr.NetworkSimulatorApplication;
import com.nokia.uwr.service.BoardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Main controller in network-simulator is responsible for starting application
 *
 * @author Piotr Stoklosa
 * @see NetworkSimulatorApplication
 */
@Controller
public class ApplicationInitializer {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationInitializer.class);

    BoardService boardService;

    @Autowired
    public ApplicationInitializer(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * startApplication method is responsible for starting whole application by running board service.
     *
     * @return state after completed process
     * @throws RuntimeException if error occurred during application
     * @author Piotr Stoklosa
     * @see BoardService#boardInitialize()
     */
    @GetMapping("/")
    @ResponseBody
    public String startApplication() {
        if (!boardService.boardInitialize()) {
            LOGGER.error("An error occurred while the application was running");
            throw new RuntimeException("Application did not finish successfully!");
        }
        return "Application finished successfully!";
    }
}
