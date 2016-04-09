/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.model.staff.AccountDetailDAO;
import myo.fpt.sample.entity.model.AccountDAO;

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
            throws ServletException, IOException, NamingException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            List<Account> acc = getJpaController().getAllAccount();
            AccountDetail accdt = null;
            //AccountManage accTmp = null;
            int accountListSize = acc.size();
            Map<Account, AccountDetail> hm = new HashMap();

            for (int i = 0; i < accountListSize; i++) {
                accdt = getJpaController().getACdetailByDetailId(acc.get(i).getDetailId());

//                Date exDate;
//                String date;
//                Date expiredDate = acc.get(i).getExpiredDate();
//                if (expiredDate == null) {
//                    exDate = null;
//                } else {
//                    SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
//                    date = sft.format(expiredDate);
//                    System.out.println("Date to string: " + date);
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//                    try {
//                        exDate = sdf.parse(date);
//                        System.out.println("String to date: "+exDate);
//                        acc.get(i).setExpiredDate(exDate);
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }

                
                hm.put(acc.get(i), accdt);
//                accTmp = new AccountManage(result.get(i).getUsername(), accdt.getDetailId(), accdt.getEmail(), accdt.getFullname(), accdt.getPhone(), accdt.getIsStaff(), accdt.getLicenseType(), exDate, accdt.getStatus());
//                resultmalayboratrangjspne.add(accTmp);
            }

            request.setAttribute("INFO", hm);
            RequestDispatcher rd = request.getRequestDispatcher(ManageUser);
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private AccountDetailDAO getJpaController() {
        try {
            return new AccountDetailDAO(getEntityManagerFactory());
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
        } catch (ParseException ex) {
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
        } catch (ParseException ex) {
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
