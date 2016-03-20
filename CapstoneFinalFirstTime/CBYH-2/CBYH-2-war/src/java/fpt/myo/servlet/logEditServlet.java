/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.servlet;

import fpt.myo.emg.EmgData;
import fpt.myo.sessionBean.logEditSessionBeanRemote;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thai
 */
public class logEditServlet extends HttpServlet {

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
            Logger logger = Logger.getLogger("RequestEditLog");
            FileHandler fh;
            ArrayList<EmgData> rightCompare = new ArrayList<EmgData>();
            ArrayList<EmgData> leftCompare = new ArrayList<EmgData>();
            ArrayList listMeaning = new ArrayList();
            ArrayList listMeaningTmpRight = new ArrayList();
            ArrayList listMeaningTmpLeft = new ArrayList();
            ArrayList posLeft = new ArrayList();
            ArrayList posRight = new ArrayList();
            String winner = null;
            int meaningLeft = 0;
            int meaningRight = 0;
            int meaningCode = 0;
            ArrayList listMeaningLeft = new ArrayList();
            ArrayList listMeaningRight = new ArrayList();
            ArrayList listMeaningCode = new ArrayList();
            String editResult = request.getParameter("editResult");
            List<String> listEdit = Arrays.asList(editResult.split("\\s*-\\s*"));
            String position = request.getParameter("position");
            System.out.println("editResult " + editResult);
            System.out.println("position " + position);
            rightCompare = (ArrayList<EmgData>) this.getServletConfig().getServletContext().getAttribute("loglRightCompare");
            leftCompare = (ArrayList<EmgData>) this.getServletConfig().getServletContext().getAttribute("loglLeftCompare");
            listMeaning = (ArrayList) this.getServletConfig().getServletContext().getAttribute("listMeaning");
            winner = (String) this.getServletConfig().getServletContext().getAttribute("win");
            posLeft = (ArrayList) this.getServletConfig().getServletContext().getAttribute("posLeft");
            posRight = (ArrayList) this.getServletConfig().getServletContext().getAttribute("posRight");
            listMeaningTmpLeft = (ArrayList) this.getServletConfig().getServletContext().getAttribute("listMeaningTmpLeft");
            listMeaningTmpRight = (ArrayList) this.getServletConfig().getServletContext().getAttribute("listMeaningTmpRight");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date now = new Date();
            String strDate = sdf.format(now);
            fh = new FileHandler("C:/Log" + "requestEditLog" + strDate + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            Context context = new InitialContext();
            Object obj = context.lookup("LEJNDI");
            logEditSessionBeanRemote poji = (logEditSessionBeanRemote) obj;
            if (poji != null) {
                for (int i = 0; i < listEdit.size(); i++) {
                    String edit = listEdit.get(i);
                    meaningCode = poji.getMeaningCodeBaseMeaning(edit);
                    listMeaningCode.add(meaningCode);
                }

                for (int i = 0; i < listMeaningCode.size(); i++) {
                    meaningLeft = poji.getMeaningLeftBaseMeaningCode((Integer) listMeaningCode.get(i));
                    listMeaningLeft.add(meaningLeft);
                }

                for (int i = 0; i < listMeaningCode.size(); i++) {
                    meaningRight = poji.getMeaningRightBaseMeaningCode((Integer) listMeaningCode.get(i));
                    listMeaningRight.add(meaningRight);
                }
            }

            logger.info("oldResult: " + listMeaning);
            logger.info("winner: " + winner);
            logger.info("edit position: " + position);
            logger.info("editResult: " + editResult);
            logger.info("leftCompare: " + leftCompare);
            logger.info("rightCompare: " + rightCompare);
            logger.info("listMeaningTmpLeft: " + listMeaningTmpLeft);
            logger.info("posLeft: " + posLeft);
            logger.info("listMeaningTmpRight: " + listMeaningTmpRight);
            logger.info("posRight: " + posRight);
            logger.info("listMeaningLeft: " + listMeaningLeft);
            logger.info("listMeaningRight: " + listMeaningRight);

        } catch (NamingException ex) {
            Logger.getLogger(logEditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
