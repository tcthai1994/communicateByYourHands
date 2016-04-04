/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.controller.AccountDetailJpaController;
import sample.check.Validate;

/**
 *
 * @author AnhND
 */
public class RegisterServlet extends HttpServlet {

    private final String loginPage = "index.jsp";

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
            String email = request.getParameter("txtEmail");
            String fullname = request.getParameter("txtFullname");
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            String re_password = request.getParameter("txtRepeatPassword");
            String phone = request.getParameter("txtPhone");
            System.out.println("password: " + password + "re password " + re_password);
            String listError = Validate.validateRegister(email, fullname, username, password, re_password, phone);//validate
            if (listError.equals("")) {
                int detailId;

                detailId = getJpaController().getDetailId();
                detailId = detailId + 1;
                boolean isStaff = false;
                String licenseType = "basic";
                Date expiredDate = null;
                boolean status = true;
                String deviceId = "";
                Account Acc = new Account(username, password, detailId, deviceId);
                AccountDetail AccDetail = new AccountDetail(detailId, email, fullname, phone, isStaff, licenseType, expiredDate, status);
                boolean checkReg = getJpaController().RegistertoAccount(Acc);
                if (checkReg) {
                    boolean checkRegDt = getJpaController().RegistertoAccountDetail(AccDetail);
                    if (checkRegDt) {

                        response.sendRedirect(loginPage);
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Register successful!');");
                        out.println("location='index.jsp';");
                        out.println("</script>");
                    }
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Register unsuccessful!');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                }
            } else {
                System.out.println(listError);
                out.println("<script type=\"text/javascript\">");
                out.println("alert('" + listError + "');");
                out.println("location='index.jsp';");
                out.println("</script>");
            }
        } finally {
            out.close();
        }
    }

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private AccountDetailJpaController getJpaController() {
        try {
            return new AccountDetailJpaController(getEntityManagerFactory());
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
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
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
