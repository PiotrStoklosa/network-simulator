package com.nokia.uwr.utility;

import com.nokia.uwr.board.Localization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocalizationCalculatorTest {

    @Test
    void distanceForPositiveLocations() {
        int distance = LocalizationCalculator.calculateDistance(new Localization(1, 2), new Localization(3, 4));
        Assertions.assertEquals(3, distance);
    }
    @Test
    void distanceForIdenticalLocations() {
        int distance = LocalizationCalculator.calculateDistance(new Localization(1, 2), new Localization(1, 2));
        Assertions.assertEquals(0, distance);
    }
    @Test
    void distanceForNegativeLocations() {
        int distance = LocalizationCalculator.calculateDistance(new Localization(-1, -2), new Localization(1, 2));
        Assertions.assertEquals(4, distance);
    }
}
