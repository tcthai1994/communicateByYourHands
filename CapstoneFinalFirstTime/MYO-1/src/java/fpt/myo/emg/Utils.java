/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.emg;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author nguyen
 */
public class Utils {

    public static boolean isEmpty(String... string) {
        boolean result = true;

        if (string.length > 0) {
            for (String s : string) {
                if (s == null || s.isEmpty() || s.length() == 0) {
                    result = false;
                }
            }
        }
        return result;
    }
}
