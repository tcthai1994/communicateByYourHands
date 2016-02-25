/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.emg;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thai
 */
public class LeftMyoArmband implements Serializable {
    @SerializedName("left")
    private ArrayList<EmgData> left;

    public LeftMyoArmband(ArrayList<EmgData> left) {
        this.left = left;
    }

    public ArrayList<EmgData> getLeft() {
        return left;
    }

    public void setLeft(ArrayList<EmgData> left) {
        this.left = left;
    }
    
    
}
