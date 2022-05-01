package com.nokia.uwr.service;

import com.nokia.uwr.model.BTS;
import com.nokia.uwr.model.UE;

import java.util.Map;

/**
 * Class responsible for calculate signals between the UE and all BTSs
 *
 * @author Barbara Moczulska
 */
public interface CalculationSignalService {

    /**
     * Calculate signals between the UE and all BTSs
     *
     * @author Barbara Moczulska
     */
    Map<BTS, Integer> calculateSignal(UE ue);

}
