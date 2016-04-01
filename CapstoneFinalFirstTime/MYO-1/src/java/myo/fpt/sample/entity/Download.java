/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity;

import java.io.Serializable;
import myo.fpt.sample.entity.MeaningRignt;
import myo.fpt.sample.entity.MeaningLeft;
import myo.fpt.sample.entity.RightSignal;
import myo.fpt.sample.entity.LeftSignal;
import myo.fpt.sample.entity.WordSignal;
import myo.fpt.sample.entity.DataContent;

/**
 *
 * @author Thai
 */
public class Download implements Serializable{
    
    private MeaningLeft ML;
    private MeaningRignt MR;
    private LeftSignal LS;
    private RightSignal RS;
    private WordSignal WS;
    private DataContent DT;

    public Download() {
    }
    
    public Download(MeaningLeft ML, MeaningRignt MR, LeftSignal LS, RightSignal RS, WordSignal WS, DataContent DT){
        this.ML = ML;
        this.MR = MR;
        this.LS = LS;
        this.RS = RS;
        this.WS = WS;
        this.DT = DT;
    }
}
