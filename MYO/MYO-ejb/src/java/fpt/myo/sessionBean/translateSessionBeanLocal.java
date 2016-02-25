/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import fpt.myo.emg.EmgData;
import fpt.myo.entityBean.LeftSignal;
import fpt.myo.entityBean.RightSignal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Thai
 */
@Local
public interface translateSessionBeanLocal {
    double distanceCalculation(EmgData streamData, EmgData compareData);
    String reConvert(EmgData dataList);
    ArrayList<EmgData> convert(ArrayList emgList);
    ArrayList<String> getAllRightEmg();
    ArrayList<String> getAllLeftEmg();
    int getMeaningRight(String emgRight);
    int getMeaningLeft(String emgLeft);
    int getMeaningCode(int meaningRight, int meaningLeft);
    String getMeaning(int meaningCode);
}
