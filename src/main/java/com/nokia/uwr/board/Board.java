package com.nokia.uwr.board;

import com.nokia.uwr.model.BTS;
import com.nokia.uwr.model.UE;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Board {

    Map<UE, Localization> ue;
    Map<BTS, Localization> bts;

    public void addBts(){}
    public void updateBts(){}
    public void addUe(){}
    public void updateUe(){}
    public void deleteUe(){}

}
