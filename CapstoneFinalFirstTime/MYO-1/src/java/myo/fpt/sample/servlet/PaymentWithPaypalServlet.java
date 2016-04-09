/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
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
import myo.fpt.sample.entity.Recipt;
import myo.fpt.sample.entity.model.staff.AccountDetailDAO;
import myo.fpt.sample.entity.model.payment.PaymentDAO;

/**
 *
 * @author AnhND
 */
public class PaymentWithPaypalServlet extends HttpServlet {

    static final long ONE_DATE = 86400000;

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
            String username = "";

            HttpSession session = request.getSession();
            username = session.getAttribute("USER").toString();

            int custId = getAccountDetailJpaController().getCustIdByUsername(username);

            int licenseId = 5;
            double premiumPrice = getPaymentJpaController().getPrice();
            double price = premiumPrice + (premiumPrice * 10 / 100);
            Date now = new Date();
            Recipt recipt = new Recipt(licenseId, now, custId, price);
            boolean checkRecipt = getPaymentJpaController().addRecipt(recipt);
            if (checkRecipt) {
                int detailId = getPaymentJpaController().findDetailIdByCustId(custId);
                String licenseType = getPaymentJpaController().getLicenseName();
                Date expiredDateOld = getPaymentJpaController().findExpiredDateByDetailId(detailId);
                System.out.println("Expiration Date lay tu database " + expiredDateOld);
                boolean checkUpdate = true;
                if (expiredDateOld == null) {
                    Calendar calendarNow = Calendar.getInstance();
                    calendarNow.setTime(now);
                    long millisecondNow = calendarNow.getTimeInMillis();
                    long real = millisecondNow + (ONE_DATE * 30);
                    Date expiredDate = new Date(real);
                    Account updated = new Account(licenseType, expiredDate);
                    checkUpdate = getPaymentJpaController().updateAfterPayment(detailId, updated);
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(expiredDateOld);
                    long millisecond = calendar.getTimeInMillis();
                    long real = millisecond + (ONE_DATE * 30);
                    Date expiredDate = new Date(real);
                    Account updated = new Account(licenseType, expiredDate);
                    checkUpdate = getPaymentJpaController().updateAfterPayment(detailId, updated);
                }
                if(checkUpdate){
                    response.sendRedirect("payment-complete.jsp");
                }
            }
        } finally {
            out.close();
        }
    }

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private AccountDetailDAO getAccountDetailJpaController() {
        try {
            return new AccountDetailDAO(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private PaymentDAO getPaymentJpaController() {
        try {
            return new PaymentDAO(getEntityManagerFactory());
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
            Logger.getLogger(PaymentWithPaypalServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PaymentWithPaypalServlet.class.getName()).log(Level.SEVERE, null, ex);
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
