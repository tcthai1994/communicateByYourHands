/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.controller.staff.AccountDetailJpaController;
import static myo.fpt.sample.servlet.PaymentWithPaypalServlet.ONE_DATE;
import sample.check.Validate;

/**
 *
 * @author AnhND
 */
public class UpdateUserAccountServlet extends HttpServlet {

    private final String AccountServlet = "AccountServlet";

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
            String action = request.getParameter("btAction");
            if (action.equals("UpdateUser")) {
                
                int detailId = Integer.parseInt(request.getParameter("txtDetailId"));
                String email = request.getParameter("txtEmail");
                String fullname = request.getParameter("txtFullname");
                String phone = request.getParameter("txtPhone");
                String active = request.getParameter("chbIsStaff");
                boolean isStaff = ("ON".equals(active));
                String licenseType = request.getParameter("txtLicenseType");
                String licenseTypeOld = request.getParameter("txtLicenseTypeOld");
                String date = request.getParameter("dtExpiredDate");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));

                String listError = Validate.validateUpdateUser(email, fullname, phone, date, licenseType);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.ENGLISH);
                if (listError.equals("")) {
                    try {
                        Date expiredDate = null;
                        if (!date.equals("")) {
                            expiredDate = formatter.parse(date);
                        }
                        String licenseTypeNew = "";
                        Date dateNew = null;
                        if (licenseType.equals(licenseTypeOld)) {
                            licenseTypeNew = licenseType;
                            dateNew = expiredDate;
                        }
                        if (licenseType.equals("basic") && licenseTypeOld.equals("premium")) {
                            licenseTypeNew = licenseType;
                            dateNew = null;
                        }
                        if (licenseType.equals("premium") && licenseTypeOld.equals("basic")) {
                            licenseTypeNew = licenseType;
                            Date now = new Date();
                            Calendar calendarNow = Calendar.getInstance();
                            calendarNow.setTime(now);
                            long millisecondNow = calendarNow.getTimeInMillis();
                            long real = millisecondNow + (ONE_DATE * 30);
                            dateNew = new Date(real);
                        }
                        AccountDetail AccDetail = new AccountDetail(detailId, email, fullname, phone, isStaff, licenseTypeNew, dateNew, status);
                        boolean checkUpdateAccDt = getJpaController().UpdateAccountDetail(detailId, AccDetail);
                        if(checkUpdateAccDt){
                            response.sendRedirect(AccountServlet);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='update-user.jsp';");
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
            Logger.getLogger(UpdateUserAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UpdateUserAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
