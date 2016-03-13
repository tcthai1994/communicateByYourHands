/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
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
import sample.entity.Account;
import sample.entity.AccountDetail;
import sample.entity.Recipt;
import sample.session.AccountDetailSessionBeanRemote;
import sample.session.PaymentSessionBeanRemote;

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
            Context context = new InitialContext();
            HttpSession session = request.getSession();
            username = session.getAttribute("USER").toString();
            Object obj = context.lookup("UAJNDI");
            AccountDetailSessionBeanRemote accountDetailRemote = (AccountDetailSessionBeanRemote) obj;
            int custId = accountDetailRemote.getCustIdByUsername(username);
            Object obj2 = context.lookup("PayJNDI");
            PaymentSessionBeanRemote paymentRemote = (PaymentSessionBeanRemote) obj2;
            int licenseId = paymentRemote.getLicenseId();
            double price = paymentRemote.getPrice();
            Date now = new Date();
            Date startDate = now;
            Recipt recipt = new Recipt(licenseId, startDate, custId, price);
            paymentRemote.addRecipt(recipt);
            int detailId = paymentRemote.findDetailIdByCustId(custId);
            String licenseType = "premium";
            Date expiredDateOld = paymentRemote.findExpiredDateByDetailId(detailId);
            System.out.println("Expiration Date lay tu database " + expiredDateOld);
            if (expiredDateOld == null) {
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.setTime(now);
                long millisecondNow = calendarNow.getTimeInMillis();
                long real = millisecondNow + (ONE_DATE * 30);
                Date expiredDate = new Date(real);
                AccountDetail updated = new AccountDetail(licenseType, expiredDate);
                paymentRemote.updateAfterPayment(detailId, updated);
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(expiredDateOld);
                long millisecond = calendar.getTimeInMillis();
                long real = millisecond + (ONE_DATE * 30);
                Date expiredDate = new Date(real);
                AccountDetail updated = new AccountDetail(licenseType, expiredDate);
                paymentRemote.updateAfterPayment(detailId, updated);
            }
            RequestDispatcher rd = request.getRequestDispatcher("payment-complete.jsp");
            rd.forward(request, response);
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
