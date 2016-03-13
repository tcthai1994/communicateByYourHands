/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
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
            List<String> listError = Validate.validateRegister(email, fullname, username, password, re_password, phone);//validate
            if (listError.isEmpty()) {
                int detailId;
                Context context = new InitialContext();
                Object obj = context.lookup("UAJNDI");
                AccountDetailSessionBeanRemote remote = (AccountDetailSessionBeanRemote) obj;

                detailId = remote.getDetailId();
                detailId = detailId + 1;
                boolean isStaff = false;
                String licenseType = "basic";
                Date expiredDate = null;
                boolean status = true;
                Account Acc = new Account(username, password, detailId);
                AccountDetail AccDetail = new AccountDetail(detailId, email, fullname, phone, isStaff, licenseType, expiredDate, status);
                remote.RegistertoAccount(Acc);
                remote.RegistertoAccountDetail(AccDetail);
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Register successful!');");
                out.println("</script>");
                RequestDispatcher rd = request.getRequestDispatcher(loginPage);
                rd.forward(request, response);
            } else {
                System.out.println(listError.size());
                for (int i = 0; i < listError.size(); i++) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError.get(i) + "');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                    System.out.println(listError.get(i));
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
