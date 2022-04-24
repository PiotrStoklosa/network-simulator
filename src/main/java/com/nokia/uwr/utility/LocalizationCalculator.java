package com.nokia.uwr.utility;

import com.nokia.uwr.board.Localization;
import lombok.experimental.UtilityClass;

/**
 * Class responsible for calculations localization
 * @see Localization
 *
 * @author Barbara Moczulska
 */
@UtilityClass
public class LocalizationCalculator {


    /**
     * Calculate distance between two point
     *
     * @param a Localization object
     * @param b Localization object
     * @return distance
     * @author Barbara Moczulska
     */
    public Integer calculateDistance(Localization a, Localization b) {

        return (int) Math.round(Math.sqrt((b.y() - a.y()) * (b.y() - a.y()) + (b.x() - a.x()) * (b.x() - a.x())));
    }


}
