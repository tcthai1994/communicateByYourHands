/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myo.fpt.sample.entity.Dictionary;
import myo.fpt.sample.entity.controller.staff.DictionaryJpaController;
import sample.check.Validation;

/**
 *
 * @author AnhND
 */
public class DictionaryProcessServlet extends HttpServlet {

    private final String DictionaryServlet = "DictionaryServlet";

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
            String button = request.getParameter("btAction");
            if (button.equals("AddDictionary")) {
                String keyword = request.getParameter("txtKeyword");
                String description = request.getParameter("txtDescription");
                String videoURL = request.getParameter("txtVideoURL");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                String listError = Validation.validateDictionary(keyword, description, videoURL);
                if (listError.equals("")) {

                    Dictionary dictionary = new Dictionary(keyword, description, videoURL, status);
                    boolean checkAddDictionary = getJpaController().addNewDictionary(dictionary);
                    if (checkAddDictionary) {
                        response.sendRedirect(DictionaryServlet);
                    }
                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='addDictionary.jsp';");
                    out.println("</script>");
                    System.out.println(listError);
                }

            } else if (button.equals("UpdateDictionary")) {
                int dictionaryId = Integer.parseInt(request.getParameter("txtInstructionId"));
                String keyword = request.getParameter("txtKeyword");
                String description = request.getParameter("txtDescription");
                String videoURL = request.getParameter("txtVideoURL");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                String listError = Validation.validateUpdateDictionary(keyword, description, videoURL);
                if (listError.equals("")) {
                    Dictionary dictionary = new Dictionary(keyword, description, videoURL, status);
                    boolean checkUpdateDictionary = getJpaController().updateDictionary(dictionaryId, dictionary);
                    if (checkUpdateDictionary) {
                        response.sendRedirect(DictionaryServlet);
                    }
                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='update-dictionary.jsp';");
                    out.println("</script>");
                    System.out.println(listError);
                }

            } else if (button.equals("DeleteDictionary")) {
                int dictionaryId = Integer.parseInt(request.getParameter("txtInstructionId"));
                boolean result = getJpaController().deleteDictionary(dictionaryId);
                response.sendRedirect(DictionaryServlet);
            }
        } finally {
            out.close();
        }
    }

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private DictionaryJpaController getJpaController() {
        try {
            return new DictionaryJpaController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
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
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            processRequest(request, response);

        } catch (NamingException ex) {
            Logger.getLogger(DictionaryProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(DictionaryProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
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
