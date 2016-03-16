/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import sample.dto.AccountManage;
import sample.entity.Account;
import sample.entity.AccountDetail;
import sample.session.AccountDetailSessionBeanRemote;

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
            HttpSession session = request.getSession();
            String sess = session.getAttribute("USER").toString();
            Context context = new InitialContext();
            Object obj = context.lookup("UAJNDI");
            AccountDetailSessionBeanRemote remote = (AccountDetailSessionBeanRemote) obj;
            List<Account> result = remote.getAllAccount();
            AccountDetail accdt = null;
            AccountManage am = null;
            List<AccountManage> resultmalayboratrangjspne = new ArrayList<AccountManage>();
            for (int i = 0; i < result.size(); i++) {
                accdt = remote.getACdetailByDetailId(result.get(i).getDetailId());
                am = new AccountManage(result.get(i).getUsername(), accdt.getDetailId(), accdt.getEmail(), accdt.getFullname(), accdt.getPhone(), accdt.getIsStaff(), accdt.getLicenseType(), accdt.getExpiredDate(), accdt.getStatus());
                resultmalayboratrangjspne.add(am);
            }
            if (result != null && sess != null && sess != "") {
                request.setAttribute("INFO", resultmalayboratrangjspne);
                RequestDispatcher rd = request.getRequestDispatcher(ManageUser);
                rd.forward(request, response);
            }
            else if(sess == null && sess.equals("")){
                RequestDispatcher rd = request.getRequestDispatcher(loginPage);
                rd.forward(request, response);
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
