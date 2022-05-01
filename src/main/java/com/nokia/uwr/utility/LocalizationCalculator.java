package com.nokia.uwr.utility;

import com.nokia.uwr.board.Localization;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class responsible for calculations localization
 *
 * @author Barbara Moczulska
 * @see Localization
 */
@UtilityClass
public class LocalizationCalculator {
    private static final Logger LOGGER = LogManager.getLogger(LocalizationCalculator.class);

    /**
     * Calculate distance between two points
     *
     * @param a Localization object
     * @param b Localization object
     * @return distance
     * @author Barbara Moczulska
     */
    public Integer calculateDistance(Localization a, Localization b) {

        LOGGER.info("Distance between points: " + a.toString() + " and " + b.toString());

        int distance = Math.toIntExact(Math.round(Math.sqrt((b.y() - a.y()) * (b.y() - a.y())
                + (b.x() - a.x()) * (b.x() - a.x()))));

        LOGGER.info("counted: " + distance);

        return distance;
    }

}
