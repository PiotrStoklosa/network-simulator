package com.nokia.uwr.board;

import com.nokia.uwr.model.BTS;
import com.nokia.uwr.model.UE;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * Contains information about localization of BTSs and UEs
 *
 * @author najedzony
 */
@Component
public class Board {

    private static final Logger LOGGER = LogManager.getLogger(Board.class);

    @Getter
    private final Map<UE, Localization> UeLocalizationHashMap = new HashMap<>();
    @Getter
    private final Map<BTS, Localization> BtsLocalizationHashMap = new HashMap<>();

    /**
     * @param bts          BTS object, that we want to add
     * @param localization localization of bts
     * @author najedzony
     */
    public void addBts(BTS bts, Localization localization) {
        LOGGER.info("Add BTS to the board");
        if (BtsLocalizationHashMap.containsKey(bts)) {
            throw new IllegalArgumentException("BTS already exists");
        }
        BtsLocalizationHashMap.put(bts, localization);
        LOGGER.info("Added BTS to the board successfully");
    }

    /**
     * @param ue           UE object, that we want to add
     * @param localization localization of ue
     * @author najedzony
     */
    public void addUe(UE ue, Localization localization) {
        LOGGER.info("Add UE to the board");
        UeLocalizationHashMap.put(ue, localization);
        LOGGER.info("Added UE to the board successfully");
    }

    /**
     * @param ue           UE object, that we want to update
     * @param localization localization of ue
     * @author najedzony
     */
    public void updateUe(UE ue, Localization localization) {
        LOGGER.info("Update position of an existing UE");
        UeLocalizationHashMap.put(ue, localization);
        LOGGER.info("Updated UE position successfully");
    }

    /**
     * @param ue UE object, that we want to delete
     * @author najedzony
     */
    public void deleteUe(UE ue) {
        LOGGER.info("Delete existing UE from the board");
        if (!UeLocalizationHashMap.containsKey(ue)) {
            throw new IllegalArgumentException("This UE doesn't exist");
        }
        UeLocalizationHashMap.remove(ue);
        LOGGER.info("Deleted UE successfully");
    }

}
