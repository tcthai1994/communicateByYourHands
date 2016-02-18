/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.servlet;

import fpt.myo.emg.EmgData;
import fpt.myo.sessionBean.translateSessionBean;
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

/**
 *
 * @author Thai
 */
public class TestServlet extends HttpServlet {
 private final static Double THRESHOLD = 0.01;
 private Double detect_distance;
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
            throws ServletException, IOException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            ArrayList<Double> test = new ArrayList<Double>();
            test.add(25.000000);
            test.add(8.000000);
            test.add(31.000000);
            test.add(36.000000);
            test.add(14.000000);
            test.add(28.000000);
            test.add(79.000000);
            test.add(20.000000);
            EmgData emg1 = new EmgData(test);

            ArrayList<Double> test2 = new ArrayList<Double>();
            test2.add(12.000000);
            test2.add(15.000000);
            test2.add(52.000000);
            test2.add(27.000000);
            test2.add(10.000000);
            test2.add(41.000000);
            test2.add(66.000000);
            test2.add(20.000000);
            EmgData emg2 = new EmgData(test2);

            ArrayList<Double> test3 = new ArrayList<Double>();
            test3.add(38.000000);
            test3.add(32.000000);
            test3.add(40.000000);
            test3.add(13.000000);
            test3.add(8.000000);
            test3.add(21.000000);
            test3.add(34.000000);
            test3.add(21.000000);
            EmgData emg3 = new EmgData(test3);

            ArrayList<Double> testt = new ArrayList<Double>();
            testt.add(38.000000);
            testt.add(32.000000);
            testt.add(40.000000);
            testt.add(13.000000);
            testt.add(8.000000);
            testt.add(21.000000);
            testt.add(34.000000);
            testt.add(21.000000);
            EmgData emgt = new EmgData(testt);
            Context context = new InitialContext();
            Object obj = context.lookup("TSJNDI");
            translateSessionBeanRemote poji = (translateSessionBeanRemote) obj;
            detect_distance = THRESHOLD;

            if (poji.distanceCalculation(emgt, emg2) < detect_distance) {
                System.out.println(poji.distanceCalculation(emgt, emg2));
                System.out.println("yay2!!!");
                detect_distance = poji.distanceCalculation(emgt, emg2);
            }
            if (poji.distanceCalculation(emgt, emg1) < detect_distance) {
                System.out.println(poji.distanceCalculation(emgt, emg1));
                System.out.println("yay1!!!");
                detect_distance = poji.distanceCalculation(emgt, emg1);
            }
            if (poji.distanceCalculation(emgt, emg3) < detect_distance) {
                System.out.println(poji.distanceCalculation(emgt, emg3));
                System.out.println("yay3!!!");
                detect_distance = poji.distanceCalculation(emgt, emg3);
            }
          
            
//            if (poji.distanceCalculation(emg2, emg1) < THRESHOLD) {
//                System.out.println(poji.distanceCalculation(emg2, emg1));
//                System.out.println("yay!!!");
//            } else {
//                System.out.println(poji.distanceCalculation(emg2, emg1));
//                System.out.println("Fuck!!");
//            }

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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
