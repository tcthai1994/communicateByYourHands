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
import sample.check.Validate;
import sample.entity.License;
import sample.session.LicenseSessionBeanRemote;

/**
 *
 * @author AnhND
 */
public class LicenseProcessServlet extends HttpServlet {

    private final String LicenseServlet = "LicenseServlet";

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
            String button = request.getParameter("btAction");
            if (button.equals("AddLicense")) {
                String licenseName = request.getParameter("txtLicensename");
                String value = request.getParameter("txtPrice");
                double price = Double.parseDouble(value);
                String description = request.getParameter("txtDescription");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                List<String> listError = Validate.validateLicense(licenseName, value, description);
                if (listError.isEmpty()) {
                    Context context = new InitialContext();
                    Object obj = context.lookup("LicJNDI");
                    LicenseSessionBeanRemote beanRemote = (LicenseSessionBeanRemote) obj;
                    if (beanRemote != null) {
                        License license = new License(licenseName, price, description, status);
                        beanRemote.addNewLicense(license);
                        RequestDispatcher rd = request.getRequestDispatcher(LicenseServlet);
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println(listError.size());
                    for (int i = 0; i < listError.size(); i++) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('" + listError.get(i) + "');");
                        out.println("location='addLicense.jsp';");
                        out.println("</script>");
                        System.out.println(listError.get(i));
                    }
                }

            } else if (button.equals("UpdateLicense")) {
                int licenseId = Integer.parseInt(request.getParameter("txtLicenseId"));
                String licenseName = request.getParameter("txtLicensename");
                String value = request.getParameter("txtPrice");
                double price = Double.parseDouble(value);
                String description = request.getParameter("txtDescription");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                List<String> listError = Validate.validateUpdateLicense(licenseName, value, description);
                if (listError.isEmpty()) {
                    Context context = new InitialContext();
                    Object obj = context.lookup("LicJNDI");
                    LicenseSessionBeanRemote beanRemote = (LicenseSessionBeanRemote) obj;
                    if (beanRemote != null) {
                        License license = new License(licenseName, price, description, status);
                        beanRemote.updateLicense(licenseId, license);
                        RequestDispatcher rd = request.getRequestDispatcher(LicenseServlet);
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println(listError.size());
                    for (int i = 0; i < listError.size(); i++) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('" + listError.get(i) + "');");
                        out.println("location='update-license.jsp';");
                        out.println("</script>");
                        System.out.println(listError.get(i));
                    }
                }
            } else if (button.equals("DeleteLicense")) {
                int licenseId = Integer.parseInt(request.getParameter("txtLicenseId"));
                Context context = new InitialContext();
                Object obj = context.lookup("LicJNDI");
                LicenseSessionBeanRemote beanRemote = (LicenseSessionBeanRemote) obj;
                boolean result = beanRemote.DeleteLicense(licenseId);
                response.sendRedirect(LicenseServlet);
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
            Logger.getLogger(LicenseProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LicenseProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
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
