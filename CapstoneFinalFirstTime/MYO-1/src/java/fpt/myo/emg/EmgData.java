/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.emg;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Thai
 */
public class EmgData implements Serializable{
    private ArrayList<Double> emgData = new ArrayList<Double>();

    public EmgData() {
    }

    public EmgData(EmgCharacteristicData characteristicData) {
        this.emgData = new ArrayList<Double>( characteristicData.getEmg8Data_abs().getEmgArray() );
    }

    public EmgData(ArrayList<Double> emgData) {
        this.emgData = emgData;
    }

    public String getLine() {
        StringBuilder return_SB = new StringBuilder();
        for (int i_emg_num = 0; i_emg_num < 8; i_emg_num++) {
            return_SB.append(String.format("%f,", emgData.get(i_emg_num)));
        }
        return return_SB.toString();
    }

    public void setLine(String line) {
        ArrayList<Double> data = new ArrayList<Double>();
        StringTokenizer st = new StringTokenizer(line , ",");
        for (int i_emg_num = 0; i_emg_num < 8; i_emg_num++) {
            data.add(Double.parseDouble(st.nextToken()));
        }
        emgData = data;
    }

    
    public void addElement(double element) {
        emgData.add(element);
    }

    public void setElement(int index ,double element) {
        emgData.set(index,element);
    }

    public Double getElement(int index) {
        if (index < 0 || index > emgData.size() - 1) {
            return null;
        } else {
            return emgData.get(index);
        }
    }

    public ArrayList<Double> getEmgArray() {
        return this.emgData;
    }

    public Double getDistanceFrom(EmgData baseData) {
        Double distance = 0.00;
        for (int i_element = 0; i_element < 8; i_element++) {
            distance += Math.pow((emgData.get(i_element) - baseData.getElement(i_element)),2.0);
        }
        return Math.sqrt(distance);
    }

//    public Double getInnerProductionTo(EmgData baseData) {
//        Double val = 0.00;
//        for (int i_element = 0; i_element < 8; i_element++) {
//            val += emgData.get(i_element) * baseData.getElement(i_element);
//        }
//        return val;
//    }

    public Double getNorm(){
        Double norm = 0.00;
        for (int i_element = 0; i_element < 8; i_element++) {
            norm += Math.pow( emgData.get(i_element) ,2.0);
        }
        return Math.sqrt(norm);
    }
    
    @Override
    public String toString(){
        String result = "";
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i<8; i++){
            stb.append(emgData.get(i).toString() + ",");
        }
        result = stb.toString();
        return result;
    }
}
