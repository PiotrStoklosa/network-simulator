package com.nokia.uwr.utility;

import com.nokia.uwr.board.Localization;
import lombok.experimental.UtilityClass;

/**
 * @author Barbara Moczulska
 */
@UtilityClass
public class LocalizationCalculator {

    public Integer calculateDistance(Localization a, Localization b) {

        return (int)Math.round(Math.sqrt((b.y() - a.y()) * (b.y() - a.y()) + (b.x() - a.x()) * (b.x() - a.x())));
    }


}
