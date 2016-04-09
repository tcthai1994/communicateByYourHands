/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myo.fpt.sample.entity.License;
import myo.fpt.sample.entity.model.staff.LicenseDAO;
import sample.check.Validation;

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
                String listError = Validation.validateLicense(licenseName, value, description);
                if (listError.equals("")) {

                    License license = new License(licenseName, price, description, status);
                    getJpaController().addNewLicense(license);
                    RequestDispatcher rd = request.getRequestDispatcher(LicenseServlet);
                    rd.forward(request, response);

                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='addLicense.jsp';");
                    out.println("</script>");
                    System.out.println(listError);
                }

            } else if (button.equals("UpdateLicense")) {
                int licenseId = Integer.parseInt(request.getParameter("txtLicenseId"));
                String licenseName = request.getParameter("txtLicensename");
                String value = request.getParameter("txtPrice");
                double price = Double.parseDouble(value);
                String description = request.getParameter("txtDescription");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                String listError = Validation.validateUpdateLicense(licenseName, value, description);
                if (listError.equals("")) {

                    License license = new License(licenseName, price, description, status);
                    boolean checkUpdateLicense = getJpaController().updateLicense(licenseId, license);
                    if (checkUpdateLicense) {
                        RequestDispatcher rd = request.getRequestDispatcher(LicenseServlet);
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='update-license.jsp';");
                    out.println("</script>");
                    System.out.println(listError);
                }
            } else if (button.equals("DeleteLicense")) {
                int licenseId = Integer.parseInt(request.getParameter("txtLicenseId"));
                boolean result = getJpaController().DeleteLicense(licenseId);
                response.sendRedirect(LicenseServlet);
            }
        } finally {
            out.close();
        }
    }

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private LicenseDAO getJpaController() {
        try {
            return new LicenseDAO(getEntityManagerFactory());
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
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
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
