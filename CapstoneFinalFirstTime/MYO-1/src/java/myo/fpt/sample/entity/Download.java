/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity;

import java.io.Serializable;
import java.util.List;
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
    
    private List<MeaningLeft> ML;
    private List<MeaningRignt> MR;
    private List<LeftSignal> LS;
    private List<RightSignal> RS;
    private List<WordSignal> WS;
    private List<DataContent> DT;

    public Download() {
    }
    
    public Download(List<MeaningLeft> ML, List<MeaningRignt> MR, List<LeftSignal> LS, List<RightSignal> RS, List<WordSignal> WS, List<DataContent> DT){
        this.ML = ML;
        this.MR = MR;
        this.LS = LS;
        this.RS = RS;
        this.WS = WS;
        this.DT = DT;
    }
}
