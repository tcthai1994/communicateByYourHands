/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.emg;
import java.util.ArrayList;
/**
 *
 * @author Thai
 */
class EmgCharacteristicData {
     private ByteReader emgData = new ByteReader();

    public EmgCharacteristicData(byte[] byteData) {
        emgData.setByteData(byteData);
    }
    public String getLine() {
        StringBuilder return_SB = new StringBuilder();
        for (int i_emg_num = 0; i_emg_num < 16; i_emg_num++) {
            return_SB.append(String.format("%d,", emgData.getByte()));
        }
        return return_SB.toString();
    }

    public EmgData getEmg8Data_abs() {
        EmgData emg8Data_max = new EmgData();
        ArrayList<Double> temp_Array = new ArrayList<Double>();
        for (int i_emg_num = 0; i_emg_num < 16; i_emg_num++) {
            double temp = emgData.getByte();
            temp_Array.add(temp);
        }
        for (int i_emg8 = 0; i_emg8 < 8; i_emg8++) {
            if (Math.abs(temp_Array.get(i_emg8)) < Math.abs(temp_Array.get(i_emg8 + 8))){
                emg8Data_max.addElement(Math.abs(temp_Array.get(i_emg8 + 8)));
            } else {
                emg8Data_max.addElement(Math.abs(temp_Array.get(i_emg8)));
            }
        }
        return emg8Data_max;
    }
    
}
