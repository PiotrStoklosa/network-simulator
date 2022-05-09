package com.nokia.uwr.service;

import com.nokia.uwr.board.Board;
import com.nokia.uwr.model.BTS;
import com.nokia.uwr.model.UE;
import com.nokia.uwr.utility.LocalizationCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Barbara Moczulska
 */
@Service
public class CalculationSignalServiceImpl implements CalculationSignalService {

    private final Board board;
    private static final Logger LOGGER = LogManager.getLogger(CalculationSignalServiceImpl.class);

    @Autowired
    public CalculationSignalServiceImpl(Board board) {
        this.board = board;
    }


    /**
     * Calculate signals between the ue and all BTSs
     *
     * @param ue UE object
     * @return HashMap with signal for all BTSs
     * @author Barbara Moczulska
     */
    @Override
    public Map<BTS, Integer> calculateSignal(UE ue) {
        Map<BTS, Integer> ueSignalHashMap = new HashMap<>();

        LOGGER.info("UE localization: " + board.getUeLocalizationHashMap().get(ue));

        board.getBtsLocalizationHashMap().forEach((key, value) -> ueSignalHashMap.put(key, LocalizationCalculator.calculateDistance(value, board.getUeLocalizationHashMap().get(ue))));

        LOGGER.info("calculated signal map: " + ueSignalHashMap);

        return ueSignalHashMap;
    }

}
