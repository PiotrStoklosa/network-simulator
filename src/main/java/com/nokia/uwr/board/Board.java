package com.nokia.uwr.board;

import com.nokia.uwr.model.BTS;
import com.nokia.uwr.model.UE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Board {

    private static final Logger LOGGER = LogManager.getLogger(Board.class);

    Map<UE, Localization> ue;
    Map<BTS, Localization> bts;

    public void addBts(){}

    public void addUe(){}
    public void updateUe(){}
    public void deleteUe(){}

}
