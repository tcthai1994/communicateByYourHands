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
public class RightMyoArmband implements Serializable {
    @SerializedName("right")
    private ArrayList<EmgData> right;

    public RightMyoArmband(ArrayList<EmgData> right) {
        this.right = right;
    }

    public ArrayList<EmgData> getRight() {
        return right;
    }

    public void setRight(ArrayList<EmgData> right) {
        this.right = right;
    }
}
