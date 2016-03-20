/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fpt.myo.entityBean.Account;
import org.json.simple.JSONObject;
import sample.session.DeviceSessionBeanRemote;

/**
 *
 * @author AnhND
 */
public class LoginFromMobileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String regId = request.getParameter("regId");
            String deviceId = request.getParameter("deviceId");
            Context context = new InitialContext();
            Object obj = context.lookup("DevJNDI");
            DeviceSessionBeanRemote beanRemote = (DeviceSessionBeanRemote) obj;
            boolean checkLogin = beanRemote.checkLogin(username, password);
            int detailId = beanRemote.getDetailIdByUsername(username);
            int custId = beanRemote.getCustIdByUsername(username);
            boolean isActive = beanRemote.isActive(detailId);
            if (checkLogin) {
                if (isActive) {
                    Account account = new Account(deviceId);
                    beanRemote.addDeviceIdToAccount(custId, account);
                    JSONObject status = new JSONObject();
                    status.put("status",1);
                    status.put("custId",custId);
                    response.getWriter().write(status.toJSONString());
                } else {
                    JSONObject status = new JSONObject();
                    status.put("status", 0);
                    response.getWriter().write(status.toJSONString());
                }
            } else {
                JSONObject status = new JSONObject();
                status.put("status", 0);
                response.getWriter().write(status.toJSONString());
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
            Logger.getLogger(LoginFromMobileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
            Logger.getLogger(LoginFromMobileServlet.class.getName()).log(Level.SEVERE, null, ex);
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
