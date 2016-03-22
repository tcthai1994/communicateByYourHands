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
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thai
 */
public class TranslateServlet extends HttpServlet {

    private final static Double THRESHOLD = 0.01;
    //private final static Double THRESHOLD = 0.005;
    private Double rDetect_distance;
    private Double lDetect_distance;
    private final int cnt = 7;

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
        while ((length = is.read(data)) != -1) {
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
                int meaningCodeLeft = 0;
                int meaningCodeRight = 0;
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
                ArrayList listMeaningTmpLeft = new ArrayList();
                ArrayList listMeaningTmpRight = new ArrayList();
                ArrayList listTmpLeft = new ArrayList();
                ArrayList listTmpRight = new ArrayList();
                ArrayList listMeaning = new ArrayList();
                ArrayList<EmgData> rightCompare = new ArrayList<EmgData>();
                ArrayList<EmgData> leftCompare = new ArrayList<EmgData>();
                ArrayList<EmgData> rightBase = new ArrayList<EmgData>();
                ArrayList<EmgData> leftBase = new ArrayList<EmgData>();
                ArrayList<EmgData> loglLeftCompare = new ArrayList<EmgData>();
                ArrayList<EmgData> loglRightCompare = new ArrayList<EmgData>();
                ArrayList posLeft = new ArrayList();
                ArrayList posRight = new ArrayList();
                boolean noGuesture = false;
                String meaning = "";
                HttpSession session = request.getSession();
//                session.setAttribute("leftCompare", "");
//                session.setAttribute("rightCompare", "");
//                session.setAttribute("listMeaning", "");
                this.getServletConfig().getServletContext().setAttribute("loglLeftCompare", "");
                this.getServletConfig().getServletContext().setAttribute("loglRightCompares", "");
                this.getServletConfig().getServletContext().setAttribute("listMeaning", "");
                this.getServletConfig().getServletContext().setAttribute("win", "");
                this.getServletConfig().getServletContext().setAttribute("posLeft", "");
                this.getServletConfig().getServletContext().setAttribute("posRight", "");
                this.getServletConfig().getServletContext().setAttribute("listMeaningTmpLeft", "");
                this.getServletConfig().getServletContext().setAttribute("listMeaningTmpRight", "");
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
                System.out.println("leftsize: " + leftCompare.size());
                System.out.println("rightsize: " + rightCompare.size());
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

                        lDetect_distance = THRESHOLD;
                        tmpLeft = null;
                        for (int m = 0; m < leftBase.size(); m++) {
                            double lDistance = poji.distanceCalculation(leftCompare.get(i), leftBase.get(m));
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
                        meaningCode = 0;
                        int mR = Integer.parseInt(listMeaningRight.get(a).toString());
                        int mL = Integer.parseInt(listMeaningLeft.get(a).toString());

                        meaningCode = poji.getMeaningCode(mR, mL);
                        if (meaningCode != 0) {
                            listMeaningTmpRight.add(meaningCode);
                            listMeaningTmpLeft.add(meaningCode);
                        } else {
                            if (mR == 0 && mL != 0) {
                                listMeaningTmpRight.add(mL);
                                listMeaningTmpLeft.add(mL);
                            } else if (mR != 0 && mL == 0) {
                                listMeaningTmpRight.add(mR);
                                listMeaningTmpLeft.add(mR);
                            } else {
                                listMeaningTmpRight.add(mR);
                                listMeaningTmpLeft.add(mL);
                            }

                        }
                    }
                    // this place is set to check manual or autodetect
                    if (true) {
                        int cntLeft = 0;
                        int cntRight = 0;
                        for (int i = 0; i < listMeaningTmpLeft.size() - 1; i++) {
                            if (listMeaningTmpLeft.get(i) == listMeaningTmpLeft.get(i + 1)) {
                                cntLeft++;
                                //System.out.println("cntLeft: " + cntLeft);
                                if (cntLeft == cnt) {
                                    listTmpLeft.add(listMeaningTmpLeft.get(i));
                                    posLeft.add(i);
                                }
                            } else {
                                cntLeft = 0;
                            }
                            if (listMeaningTmpRight.get(i) == listMeaningTmpRight.get(i + 1)) {
                                cntRight++;
                                //System.out.println("contRight: " + cntRight);
                                if (cntRight == cnt) {
                                    listTmpRight.add(listMeaningTmpRight.get(i));
                                    posRight.add(i);
                                }
                            } else {
                                cntRight = 0;
                            }
                        }
                    } else {
                        
                    }

                    System.out.println("right size " + listTmpRight.size());
                    System.out.println("left size " + listTmpLeft.size());
                    if (listTmpRight.size() < listTmpLeft.size()) {
                        System.out.println("left win");
                        listMeaningCode = listTmpLeft;
                        this.getServletConfig().getServletContext().setAttribute("win", "left");
                    } else {
                        System.out.println("right win");
                        this.getServletConfig().getServletContext().setAttribute("win", "right");
                        listMeaningCode = listTmpRight;
                    }

                    System.out.println("Find code done!!!");
                    //Find meaning
                    if (!listMeaningCode.isEmpty()) {
                        listMeaning.add("");
                        for (int b = 0; b < listMeaningCode.size(); b++) {
                            int mC = Integer.parseInt(listMeaningCode.get(b).toString());
                            meaning = poji.getMeaning(mC);
                            if (meaning != null && !meaning.equals(listMeaning.get(listMeaning.size() - 1).toString())) {
                                listMeaning.add(meaning);
                            }
                        }
                    }
                    System.out.println("I have the meaning!!!!!!!!!!");
                    if (!listMeaning.isEmpty()) {
                        listMeaning.remove(0);
                    }
                    if (listMeaning.isEmpty()) {
                        noGuesture = true;
                    }

                    if (noGuesture == false) {
//                        for(int i = 1; i< listMeaning.size(); i++){
//                        String preWord = listMeaning.get(i-1).toString();
//                        String postWord = listMeaning.get(i).toString();
//                        if (preWord.equals(postWord)){
//                            listMeaning.remove(postWord);
//                        }
//                    }

//                        List filter = new ArrayList();
//                        int count = 0;
//                        for (int i = 0; i < listMeaning.size()-1; i++) {
//                            if (listMeaning.get(i).equals(listMeaning.get(i+1))) {
//                                count++;
//                                if(count==3){
//                                    filter.add(listMeaning.get(i));
//                                }
//                            } else{
//                                count = 0;
//                            }
//                        }
                        String result = gson.toJson(listMeaning);
                        System.out.println(result);
                        System.out.println("raw left size: " + leftCompare.size());
                        System.out.println("raw right size: " + rightCompare.size());
//                        session.setAttribute("leftCompare", leftCompare);
//                        session.setAttribute("rightCompare", rightCompare);
//                        session.setAttribute("listMeaning", listMeaning);

                        //get 7 - emg datas of detected guesture to log
                        //left
                        for (int i = 0; i < posLeft.size(); i++) {
                            int l = (Integer) posLeft.get(i);
                            for (int j = cnt; j > 0; j--) {
                                loglLeftCompare.add(leftCompare.get(l));
                                l--;
                            }
                        }

                        //right
                        for (int i = 0; i < posRight.size(); i++) {
                            int r = (Integer) posRight.get(i);
                            for (int j = cnt; j > 0; j--) {
                                loglRightCompare.add(rightCompare.get(r));
                                r--;
                            }
                        }

                        this.getServletConfig().getServletContext().setAttribute("loglLeftCompare", loglLeftCompare);
                        this.getServletConfig().getServletContext().setAttribute("loglRightCompare", loglRightCompare);
                        this.getServletConfig().getServletContext().setAttribute("listMeaning", listMeaning);
                        this.getServletConfig().getServletContext().setAttribute("posRight", posRight);
                        this.getServletConfig().getServletContext().setAttribute("posLeft", posLeft);
                        this.getServletConfig().getServletContext().setAttribute("listMeaningTmpRight", listMeaningTmpRight);
                        this.getServletConfig().getServletContext().setAttribute("listMeaningTmpLeft", listMeaningTmpLeft);
                        response.getWriter().write(result);
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
             } */ finally {
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
