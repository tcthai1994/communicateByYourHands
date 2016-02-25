/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.servlet;

import com.google.gson.Gson;
import fpt.myo.sessionBean.translateSessionBeanRemote;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import fpt.myo.emg.EmgData;
import fpt.myo.emg.LeftMyoArmband;
import fpt.myo.emg.MyoSignal;
import fpt.myo.emg.RightMyoArmband;
import java.io.InputStream;
import java.util.Arrays;

/**
 *
 * @author Thai
 */
public class TranslateServlet extends HttpServlet {

    private final static Double THRESHOLD = 0.01;
    private Double rDetect_distance;
    private Double lDetect_distance;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //String rEmgJson = request.getParameter("rEmgJson");
        //String lEmgJson = request.getParameter("lEmgJson");
        InputStream is = request.getInputStream();
        byte[] data = new byte[4096];
        int length;
        StringBuilder sb = new StringBuilder();
        while((length = is.read(data))!=-1){
            sb.append(new String(data, 0, length));
        }
        String inputData = sb.toString();
        System.out.println("sb: " + inputData);
        //System.out.println(rEmgJson);
        //System.out.println(lEmgJson);
        if (inputData != null && !inputData.equals("")) {
            JSONObject jsonL;
            JSONObject jsonR;
            try {
                int meaningCode = 0;
                EmgData tmpRight = new EmgData();
                EmgData tmpLeft = new EmgData();
                String matchRight = "";
                String matchLeft = "";
                int meaningRight = 0;
                int meaningLeft = 0;
                ArrayList listMatchRight = new ArrayList();
                ArrayList listMatchLeft = new ArrayList();
                ArrayList listMeaningRight = new ArrayList();
                ArrayList listMeaningLeft = new ArrayList();
                ArrayList listMeaningCode = new ArrayList();
                ArrayList listMeaning = new ArrayList();
                ArrayList<EmgData> rightCompare = new ArrayList<EmgData>();
                ArrayList<EmgData> leftCompare = new ArrayList<EmgData>();
                ArrayList<EmgData> rightBase = new ArrayList<EmgData>();
                ArrayList<EmgData> leftBase = new ArrayList<EmgData>();
                boolean noGuesture = false;
                String meaning = "";
                //jsonR = (JSONObject) new JSONParser().parse(rEmgJson);
                //jsonL = (JSONObject) new JSONParser().parse(lEmgJson);
                //String rRawJson = jsonR.get("right").toString();
                //String lRawJson = jsonL.get("left").toString();
                //ArrayList<String> StrRightJson = new ArrayList<String>(Arrays.asList(rRawJson.split("\\s*/\\s*")));
                //ArrayList<String> StrLeftJson = new ArrayList<String>(Arrays.asList(lRawJson.split("\\s*/\\s*")));
                Context context = new InitialContext();
                Object obj = context.lookup("TSJNDI");
                Gson gson = new Gson();
                MyoSignal myo = gson.fromJson(inputData, MyoSignal.class);
                LeftMyoArmband lMyoArmband = myo.getlEmgJson();
                RightMyoArmband rMyoArmband = myo.getrEmgJson();
                leftCompare = lMyoArmband.getLeft();
                rightCompare = rMyoArmband.getRight();
                translateSessionBeanRemote poji = (translateSessionBeanRemote) obj;
                if (poji != null) {
                    //Get Data in Database
                    ArrayList StrRightEmg = poji.getAllRightEmg();
                    ArrayList StrLeftEmg = poji.getAllLeftEmg();
                    System.out.println("Get data done!!!");
                    //rightCompare = poji.convert(StrRightJson);
                    // leftCompare = poji.convert(StrLeftJson);
                    //convert String to EMG
                    rightBase = poji.convert(StrRightEmg);
                    leftBase = poji.convert(StrLeftEmg);
                    System.out.println("Convert done");
//                    for (int i = 0; i < rightCompare.size(); i++) {
//                        System.out.println(rightCompare.get(i).toString());
//                        System.out.println(leftCompare.get(i).toString());
//                    }
//                    for (int i = 0; i < rightBase.size(); i++) {
//                        System.out.println(rightBase.get(i).toString());
//                        System.out.println(leftBase.get(i).toString());
//                    }
                    rDetect_distance = THRESHOLD;
                    lDetect_distance = THRESHOLD;
                    //Find matched right gesture
                    for (int i = 0; i < rightCompare.size(); i++) {
                        rDetect_distance = THRESHOLD;
                        tmpRight = null;
                        for (int j = 0; j < rightBase.size(); j++) {
                            double rDistance = poji.distanceCalculation(rightCompare.get(i), rightBase.get(j));
                            if (rDistance < rDetect_distance) {
                                rDetect_distance = rDistance;
                                tmpRight = rightBase.get(j);
                            }
                            if (!(tmpRight == null)) {
//                                System.out.println("a");
//                                System.out.println(tmpRight);
//                                System.out.println("oc");
                                matchRight = poji.reConvert(tmpRight);
//                                System.out.println("b");
                            }
                        }
                        if (matchRight != null) {
                            //System.out.println("c");
                            listMatchRight.add(matchRight);
                            //System.out.println("d");
                        }
                    }

                    //Find matched left gesture
                    for (int n = 0; n < leftCompare.size(); n++) {
                        lDetect_distance = THRESHOLD;
                        tmpLeft = null;
                        for (int m = 0; m < leftBase.size(); m++) {
                            double lDistance = poji.distanceCalculation(leftCompare.get(n), leftBase.get(m));
                            //System.out.println("lD " + lDistance);
                            if (lDistance < lDetect_distance) {
                                lDetect_distance = lDistance;
                                tmpLeft = leftBase.get(m);
                            }
                            if (!(tmpLeft == null)) {
                                matchLeft = poji.reConvert(tmpLeft);
                            }
                        }
                        if (matchLeft != null) {
                            listMatchLeft.add(matchLeft);
                        }
                    }
                    System.out.println("Mathching raw ges!!");
                    //Find meaningRight
                    for (int x = 0; x < listMatchRight.size(); x++) {
                        String emgCodeR = listMatchRight.get(x).toString();
                        //System.out.println("R:" + emgCodeR);
                        meaningRight = poji.getMeaningRight(emgCodeR);
                        System.out.println("R " + meaningRight);
                        listMeaningRight.add(meaningRight);
                        System.out.println("rDone!");
                    }
                    //Find meaningLeft
                    for (int y = 0; y < listMatchLeft.size(); y++) {
                        String emgCodeL = listMatchLeft.get(y).toString();
                        //System.out.println("L:" + emgCodeL);
                        meaningLeft = poji.getMeaningLeft(emgCodeL);
                        System.out.println("L " + meaningLeft);
                        listMeaningLeft.add(meaningLeft);
                        System.out.println("lDone!");
                    }
                    System.out.println("Find meaning each done!!!");
                    //Find meaningCode
                    for (int a = 0; a < listMeaningRight.size(); a++) {
                        int mR = Integer.parseInt(listMeaningRight.get(a).toString());
                        int mL = Integer.parseInt(listMeaningLeft.get(a).toString());
                        meaningCode = poji.getMeaningCode(mR, mL);
                        listMeaningCode.add(meaningCode);
                    }
                    System.out.println("Find code done!!!");
                    //Find meaning
                    if (!listMeaningCode.isEmpty()) {
                        for (int b = 0; b < listMeaningCode.size(); b++) {
                            int mC = Integer.parseInt(listMeaningCode.get(b).toString());
                            meaning = poji.getMeaning(mC);
                            if (meaning != null) {
                                listMeaning.add(meaning);
                            }
                        }
                    }
                    System.out.println("I have the meaning!!!!!!!!!!");
                    if (listMeaning.isEmpty()) {
                        noGuesture = true;
                    }

                    if (noGuesture == false) {
                        String result = gson.toJson(listMeaning);
                        System.out.println(result);
                        response.getWriter().write(result);
                        for (int i = 0; i < listMeaning.size(); i++) {
                            System.out.println(listMeaning.get(i));
                        }
                    } else {
                        JSONObject error = new JSONObject();
                        error.put("Error", "NoGesture");
                        response.getWriter().write(error.toJSONString());
                        System.out.println("No guesture");
                    }

                } else {
                    System.out.println("truyen kinh di o day ne");
                }
            } catch (NamingException ex) {
                Logger.getLogger(TranslateServlet.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (ParseException ex) {
                Logger.getLogger(TranslateServlet.class.getName()).log(Level.SEVERE, null, ex);
            } */finally {
                out.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
