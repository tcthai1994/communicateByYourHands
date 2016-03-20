/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import fpt.myo.entityBean.DataContent;
import fpt.myo.entityBean.WordSignal;
import fpt.myo.entityBean.WordSignalPK;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Thai
 */
@Remote
public interface trainSessionBeanRemote {

    int getMeaningLeft();

    int getMeaningRight();

    void setMeaningLeft(int meaningLeft);

    void setMeaningRight(int meaningRight);

    void setLeftSignal(String emgCode, int meaningLeft, boolean isCustom);

    void setRightSignal(String emgCode, int meaningRight, boolean isCustom);

    int getMeaningCode(String s);

    boolean isMeaningExist(String s);

    public List<Integer> getMeaningLeftFromMeaningCode(int meaningCode);

    void setDataContent(DataContent dT);

    void setWordSignal(WordSignal wS);

    boolean isMeaningLeftExits(int mL);

    boolean isMeaningRightExits(int mR);
    
    boolean isKeyWordSignalExits(WordSignalPK wsp);
}
