/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.servlet;

import com.google.gson.Gson;
import fpt.myo.emg.LeftMyoArmband;
import fpt.myo.entityBean.WordSignalPK;
import fpt.myo.entityBean.WordSignal;
import fpt.myo.emg.MyoSignal;
import fpt.myo.emg.RightMyoArmband;
import fpt.myo.entityBean.DataContent;
import fpt.myo.sessionBean.trainSessionBeanRemote;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
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

/**
 *
 * @author Thai
 */
public class TrainServlet extends HttpServlet {

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
        try {
            /* TODO output your page here. You may use following sample code. */
            String meaning = request.getParameter("meaning");
            String leftData = request.getParameter("leftData");
            String rightData = request.getParameter("rightData");
            String leftMeaning = request.getParameter("leftMeaning");
            String rightMeaning = request.getParameter("rightMeaning");
            boolean isCustom = false;
            boolean isContinue = true;
            int libraryId = 1;
            if (meaning != null && leftData != null && rightData != null) {
                Context context = new InitialContext();
                Object obj = context.lookup("TJNDI");
                trainSessionBeanRemote poji = (trainSessionBeanRemote) obj;
                if (poji != null) {
//                    int meaningLeft = poji.getMeaningLeft() + 1;
//                    int meaningRight = poji.getMeaningRight() + 1;
//                    //set meaingLeft
//                    poji.setMeaningLeft(meaningLeft);
//                    //set meaningRight
//                    poji.setMeaningRight(meaningRight);
//                    //set leftSignal
//                    poji.setLeftSignal(leftData, meaningLeft, isCustom);
//                    //set rightSignal
//                    poji.setRightSignal(rightData, meaningRight, isCustom);
//
//                    //set dataContent
//                    if (!poji.isMeaningExist(meaning)) {
//                        System.out.println("Insert lan dau");
//                        DataContent dT = new DataContent(meaning, libraryId);
//                        poji.setDataContent(dT);
//                        WordSignalPK wPk = new WordSignalPK(meaningLeft, meaningRight);
//                        int meaningCode = poji.getMeaningCode(meaning);
//                        WordSignal wS = new WordSignal(wPk, meaningCode);
//                        poji.setWordSignal(wS);
//                    } else {
//                        System.out.println("Co roi choi moi!!!");
//                        int meaningCode = poji.getMeaningCode(meaning);
//                        WordSignalPK wPk = new WordSignalPK(meaningLeft, meaningRight);
//                        WordSignal wS = new WordSignal(wPk, meaningCode);
//                        poji.setWordSignal(wS);
//                        List<Integer> lInt = poji.getMeaningLeftFromMeaningCode(meaningCode);
//                        for (int i = 0; i < lInt.size() - 1; i++) {
//                            wPk = new WordSignalPK(lInt.get(i), meaningRight);
//                            wS = new WordSignal(wPk, meaningCode);
//                            poji.setWordSignal(wS);
//                            wPk = new WordSignalPK(meaningLeft, lInt.get(i));
//                            wS = new WordSignal(wPk, meaningCode);
//                            poji.setWordSignal(wS);
//                        }
//                    }
//                    //set wordSignal
//
//                    response.getWriter().write("Save successfully");
//                    System.out.println("Done");

                    //check da co tu nay trong DB chua
                    if (!poji.isMeaningExist(meaning)) {
                        int leftCode = 0;
                        int rightCode = 0;
                        System.out.println("Insert lan dau");
                        DataContent dT = new DataContent(meaning, libraryId);
                        poji.setDataContent(dT);
                        if (poji.isMeaningExist(leftMeaning)) {
                            leftCode = poji.getMeaningCode(leftMeaning);
                            if (!poji.isMeaningLeftExits(leftCode)) {
                                poji.setMeaningLeft(leftCode);
                            }
                            poji.setLeftSignal(leftData, leftCode, isCustom);
                        } else {
                            isContinue = false;
                        }
                        if (poji.isMeaningExist(rightMeaning) && isContinue == true) {
                            rightCode = poji.getMeaningCode(rightMeaning);
                            if (!poji.isMeaningLeftExits(rightCode)) {
                                poji.setMeaningRight(rightCode);
                            }
                            poji.setRightSignal(rightData, rightCode, isCustom);
                        } else {
                            isContinue = false;
                        }
                        if (isContinue == true) {
                            WordSignalPK wPk = new WordSignalPK(leftCode, rightCode);
                            if (!poji.isKeyWordSignalExits(wPk)) {
                                WordSignal wS = new WordSignal(wPk, poji.getMeaningCode(meaning));
                                poji.setWordSignal(wS);
                            }
                        }
                    } else {
                        int leftCode = 0;
                        int rightCode = 0;
                        System.out.println("Co roi choi moi!!!");
                        if (poji.isMeaningExist(leftMeaning)) {
                            leftCode = poji.getMeaningCode(leftMeaning);
                            if (!poji.isMeaningLeftExits(leftCode)) {
                                poji.setMeaningLeft(leftCode);
                            }
                            poji.setLeftSignal(leftData, leftCode, isCustom);
                        } else {
                            isContinue = false;
                        }
                        if (poji.isMeaningExist(rightMeaning) && isContinue == true) {
                            rightCode = poji.getMeaningCode(rightMeaning);
                            if (!poji.isMeaningRightExits(rightCode)) {
                                poji.setMeaningRight(rightCode);
                            }
                            poji.setRightSignal(rightData, rightCode, isCustom);
                        } else {
                            isContinue = false;
                        }
                        if (isContinue == true) {
                            WordSignalPK wPk = new WordSignalPK(leftCode, rightCode);
                            if (!poji.isKeyWordSignalExits(wPk)) {
                                WordSignal wS = new WordSignal(wPk, poji.getMeaningCode(meaning));
                                poji.setWordSignal(wS);
                            }
                        }
                    }
                    if (isContinue == true) {
                        response.getWriter().write("Save successfully");
                        System.out.println("Done");
                    } else {
                        response.getWriter().write("Save fail");
                        System.out.println("fail");
                    }
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(TrainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
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
