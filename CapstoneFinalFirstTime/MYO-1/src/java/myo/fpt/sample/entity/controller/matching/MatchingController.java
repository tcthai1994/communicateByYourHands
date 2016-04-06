/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller.matching;

import fpt.myo.emg.EmgData;
import java.util.ArrayList;

/**
 *
 * @author nguyen
 */
public class MatchingController {

    public static ArrayList findMatching(ArrayList<EmgData> Base, ArrayList<EmgData> Compare) {
        final Double THRESHOLD = 0.005;
        //private final static Double THRESHOLD = 0.005;
        Double detect_distance;
        ArrayList result = new ArrayList();
        String match = "";
        EmgData tmpEmg = new EmgData();
        for (int i = 0; i < Compare.size(); i++) {

            detect_distance = THRESHOLD;
            tmpEmg = null;
            for (int j = 0; j < Base.size(); j++) {
                double Distance = distanceCalculation(Compare.get(i), Base.get(j));
                if (Distance < detect_distance) {
                    detect_distance = Distance;
                    tmpEmg = Base.get(j);
                }
                if (!(tmpEmg == null)) {
                    match = reConvert(tmpEmg);
                }
                if (Distance == 0) {
                    break;
                }
            }
            if (match != null) {
                result.add(match);
            }
        }
        return result;
    }

    private static double distanceCalculation(EmgData streamData, EmgData compareData) {
        double return_val = streamData.getDistanceFrom(compareData) / streamData.getNorm() / compareData.getNorm();
        return return_val;
    }

    private static String reConvert(EmgData dataList) {
        return dataList.toString();
    }
}
