/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import sample.check.Validate;
import sample.entity.Account;
import sample.entity.AccountDetail;
import sample.session.AccountDetailSessionBeanRemote;

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
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                String date = request.getParameter("dtExpiredDate");
                List<String> listError = Validate.validateUpdateUser(email, fullname, phone, date, licenseType);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
                        Locale.ENGLISH);
                if (listError.isEmpty()) {
                    try {
                        Date expiredDate = formatter.parse(date);
                        Context context = new InitialContext();
                        Object object = context.lookup("UAJNDI");
                        AccountDetailSessionBeanRemote remote = (AccountDetailSessionBeanRemote) object;
                        if (remote != null) {
                            AccountDetail AccDetail = new AccountDetail(detailId, email, fullname, phone, isStaff, licenseType, expiredDate, status);
                            remote.UpdateAccountDetail(detailId, AccDetail);
                        }
                        RequestDispatcher rd = request.getRequestDispatcher(AccountServlet);
                        rd.forward(request, response);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(listError.size());
                    for (int i = 0; i < listError.size(); i++) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('" + listError.get(i) + "');");
                        out.println("location='update-user.jsp';");
                        out.println("</script>");
                        System.out.println(listError.get(i));
                    }
                }
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
        try {
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
