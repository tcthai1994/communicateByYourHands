/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.controller.staff.AccountDetailJpaController;
import sample.check.Validation;

/**
 * 
 * @author AnhND
 */
public class UserUpdateServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("btAction");
            if (action.equals("UserUpdateProfile")) {
                int detailId = Integer.parseInt(request.getParameter("txtDetailId"));
                String username = request.getParameter("txtUsername");
                String email = request.getParameter("txtEmail");
                String fullname = request.getParameter("txtFullname");
                String phone = request.getParameter("txtPhone");
                String password_new = request.getParameter("txtPassword");
                String re_password = request.getParameter("txtRepeatpassword");
                String password_old = request.getParameter("txtPasswordOld");
                System.out.println("old password " + password_old);
                System.out.println("new password " + password_new);
                String password = "";
                String listError = Validation.validateUserUpdate(email, fullname, phone, password_new, re_password);
                if (listError.equals("")) {
                    if (!password_new.equals("") && password_new != null) {
                        password = password_new;
                    } else {
                        password = password_old;
                    }
                    int custid = getJpaController().getCustIdByUsername(username);
                    Account acc = new Account(username, password);
                    AccountDetail accDetail = new AccountDetail(email, fullname, phone);
                    boolean checkUpdateAcc = getJpaController().userUpdateAccount(custid, acc);
                    if (checkUpdateAcc) {
                        boolean checkUpdateAccDt = getJpaController().userUpdateAccountDetail(detailId, accDetail);
                        if (checkUpdateAccDt) {
                            response.sendRedirect("LoadDictionaryPageServlet");
                        }
                    }
                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='user-profile.jsp';");
                    out.println("</script>");
                }
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
            Logger.getLogger(UserUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UserUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
