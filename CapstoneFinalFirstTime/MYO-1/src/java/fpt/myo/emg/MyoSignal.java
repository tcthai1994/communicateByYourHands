/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.emg;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Thai
 */
public class MyoSignal {

    @SerializedName("lEmgJson")
    private LeftMyoArmband lEmgJson;

    @SerializedName("rEmgJson")
    private RightMyoArmband rEmgJson;
    
    @SerializedName("mode")
    private int mode;
    
    public MyoSignal(LeftMyoArmband lEmgJson, RightMyoArmband rEmgJson, int mode) {
        this.lEmgJson = lEmgJson;
        this.rEmgJson = rEmgJson;
        this.mode = mode;
    }

    public LeftMyoArmband getlEmgJson() {
        return lEmgJson;
    }

    public void setlEmgJson(LeftMyoArmband lEmgJson) {
        this.lEmgJson = lEmgJson;
    }

    public RightMyoArmband getrEmgJson() {
        return rEmgJson;
    }

    public void setrEmgJson(RightMyoArmband rEmgJson) {
        this.rEmgJson = rEmgJson;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
    
    
    
    

}
