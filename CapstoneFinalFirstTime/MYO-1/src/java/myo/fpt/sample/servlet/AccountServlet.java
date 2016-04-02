/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.dto.AccountManage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.controller.AccountDetailJpaController;
import myo.fpt.sample.entity.controller.AccountJpaController;

/**
 *
 * @author AnhND
 */
public class AccountServlet extends HttpServlet {

    private final String ManageUser = "homePage.jsp";
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
            
            List<Account> result = getJpaController().getAllAccount();
            AccountDetail accdt = null;
            AccountManage am = null;
            List<AccountManage> resultmalayboratrangjspne = new ArrayList<AccountManage>();
            for (int i = 0; i < result.size(); i++) {
                accdt = getJpaController().getACdetailByDetailId(result.get(i).getDetailId());
                Date date = accdt.getExpiredDate();
                String exDate = "";
                if (date != null) {
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    exDate = ft.format(date);
                }
                am = new AccountManage(result.get(i).getUsername(), accdt.getDetailId(), accdt.getEmail(), accdt.getFullname(), accdt.getPhone(), accdt.getIsStaff(), accdt.getLicenseType(), exDate, accdt.getStatus());
                resultmalayboratrangjspne.add(am);
            }
            if (result != null ) {
                request.setAttribute("INFO", resultmalayboratrangjspne);
                RequestDispatcher rd = request.getRequestDispatcher(ManageUser);
                rd.forward(request, response);
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
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
