/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AnhND
 */
public class CenterServlet extends HttpServlet {

    private final String loginServlet = "LoginServlet";
    private final String registerServlet = "RegisterServlet";
    private final String DictionaryProcessServlet = "DictionaryProcessServlet";
    private final String UpdateUserAccountServlet = "UpdateUserAccountServlet";
    private final String LibraryProcessServlet = "LibraryProcessServlet";
    private final String LicenseProcessServlet = "LicenseProcessServlet";
    private final String UserUpdateServlet = "UserUpdateServlet";
    private final String SearchProcessServlet = "SearchProcessServlet";

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
            String action = request.getParameter("btAction");
            if (action.equals("Login")) {
                RequestDispatcher rd = request.getRequestDispatcher(loginServlet);
                rd.forward(request, response);
            } else if (action.equals("Register")) {
                RequestDispatcher rd = request.getRequestDispatcher(registerServlet);
                rd.forward(request, response);
            } else if (action.equals("AddDictionary")) {
                RequestDispatcher rd = request.getRequestDispatcher(DictionaryProcessServlet);
                rd.forward(request, response);
            } else if (action.equals("UpdateDictionary")) {
                RequestDispatcher rd = request.getRequestDispatcher(DictionaryProcessServlet);
                rd.forward(request, response);
            } else if (action.equals("UpdateUser")) {
                RequestDispatcher rd = request.getRequestDispatcher(UpdateUserAccountServlet);
                rd.forward(request, response);
            } else if (action.equals("AddLibrary")) {
                RequestDispatcher rd = request.getRequestDispatcher(LibraryProcessServlet);
                rd.forward(request, response);
            } else if (action.equals("UpdateLibrary")) {
                RequestDispatcher rd = request.getRequestDispatcher(LibraryProcessServlet);
                rd.forward(request, response);
            } else if (action.equals("UpdateLicense")) {
                RequestDispatcher rd = request.getRequestDispatcher(LicenseProcessServlet);
                rd.forward(request, response);
            } else if (action.equals("UserUpdateProfile")) {
                RequestDispatcher rd = request.getRequestDispatcher(UserUpdateServlet);
                rd.forward(request, response);
            } else if (action.equals("Find")) {
                RequestDispatcher rd = request.getRequestDispatcher(SearchProcessServlet);
                rd.forward(request, response);
            } else if (action.equals("Search")) {
                RequestDispatcher rd = request.getRequestDispatcher(SearchProcessServlet);
                rd.forward(request, response);
            }
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
