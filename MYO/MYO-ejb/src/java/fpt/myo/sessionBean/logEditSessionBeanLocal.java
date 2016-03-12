/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import javax.ejb.Local;

/**
 *
 * @author Thai
 */
@Local
public interface logEditSessionBeanLocal {
    int getMeaningCodeBaseMeaning(String meaning);
    int getMeaningLeftBaseMeaningCode(int meaningCode);
    int getMeaningRightBaseMeaningCode(int meaningCode);
}
