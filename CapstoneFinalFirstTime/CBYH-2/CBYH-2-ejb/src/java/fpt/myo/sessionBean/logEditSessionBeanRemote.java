/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import javax.ejb.Remote;

/**
 *
 * @author Thai
 */
@Remote
public interface logEditSessionBeanRemote {
    int getMeaningCodeBaseMeaning(String meaning);
    int getMeaningLeftBaseMeaningCode(int meaningCode);
    int getMeaningRightBaseMeaningCode(int meaningCode);
}
