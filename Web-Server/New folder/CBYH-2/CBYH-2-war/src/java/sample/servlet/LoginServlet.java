/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;
import sample.entity.Account;
import sample.entity.Notification;
import sample.session.AccountDetailSessionBeanRemote;
import sample.session.LoginSessionBeanRemote;
import sample.session.NotificationSessionBeanRemote;

/**
 *
 * @author AnhND
 */
public class LoginServlet extends HttpServlet {

    private final String ManageUser = "AccountServlet";
    private final String DictionaryPage = "LoadDictionaryPageServlet";

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
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            LoginSessionBeanRemote remote = null;
            Context context = new InitialContext();
            Object obj = context.lookup("LoginJNDI");
            remote = (LoginSessionBeanRemote) obj;
            boolean result = remote.checkLogin(username, password);
            int detailId = remote.getDetailIdByUsername(username);
            boolean status = remote.isActive(detailId);
            boolean isStaff = remote.isAdmin(detailId);

            if (result) {
                if (status) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", username);
                    Object obj1 = context.lookup("UAJNDI");
                    AccountDetailSessionBeanRemote accountDetailRemote = (AccountDetailSessionBeanRemote) obj1;
                    /**
                     * Notification
                     */
                    Object obj2 = context.lookup("NotiJNDI");
                    NotificationSessionBeanRemote notificationSessionBeanRemote = (NotificationSessionBeanRemote) obj2;
                    Account account = accountDetailRemote.findByUsername(username);
                    List<Notification> notifications = notificationSessionBeanRemote.findNewNotificationsByCustId(account.getCustId());
                    if (notifications.size() > 0) {
                        session.setAttribute("NOTIFICATION", notifications);
                    }
                    if (!isStaff) {
                        RequestDispatcher rd = request.getRequestDispatcher(DictionaryPage);
                        rd.forward(request, response);
                    } else {
                        session.setAttribute("isAdmin", isStaff);
                        RequestDispatcher rd = request.getRequestDispatcher(ManageUser);
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println(status);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Incorrect username or password, please try again.');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                }
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Incorrect username or password, please try again.');");
                out.println("location='index.jsp';");
                out.println("</script>");
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
