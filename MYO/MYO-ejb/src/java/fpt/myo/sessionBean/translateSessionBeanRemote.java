/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import fpt.myo.emg.EmgData;
import javax.ejb.Remote;

/**
 *
 * @author Thai
 */
@Remote
public interface translateSessionBeanRemote {
    double distanceCalculation(EmgData streamData, EmgData compareData);
}
