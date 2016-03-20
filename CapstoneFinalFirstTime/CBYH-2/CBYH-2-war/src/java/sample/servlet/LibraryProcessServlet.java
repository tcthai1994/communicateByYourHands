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
import fpt.myo.entityBean.Library;
import sample.session.LibrarySessionBeanRemote;

/**
 *
 * @author AnhND
 */
public class LibraryProcessServlet extends HttpServlet {

    private final String LibraryServlet = "LibraryServlet";

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
            if (button.equals("AddLibrary")) {
                String libraryName = request.getParameter("txtLibraryname");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                String listError = Validate.validateLibrary(libraryName);
                if (listError.equals("")) {
                    Context context = new InitialContext();
                    Object obj = context.lookup("LibJNDI");
                    LibrarySessionBeanRemote beanRemote = (LibrarySessionBeanRemote) obj;
                    if (beanRemote != null) {
                        Library lib = new Library(libraryName, status);
                        beanRemote.addNewLibrary(lib);
                        RequestDispatcher rd = request.getRequestDispatcher(LibraryServlet);
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='addLibrary.jsp';");
                    out.println("</script>");
                    System.out.println(listError);
                }

            } else if (button.equals("UpdateLibrary")) {
                int libraryId = Integer.parseInt(request.getParameter("txtLibraryId"));
                String libraryName = request.getParameter("txtLibraryname");
                String active2 = request.getParameter("chbStatus");
                boolean status = ("ON".equals(active2));
                String listError = Validate.validateUpdateLibrary(libraryName);
                if (listError.equals("")) {
                    Context context = new InitialContext();
                    Object obj = context.lookup("LibJNDI");
                    LibrarySessionBeanRemote beanRemote = (LibrarySessionBeanRemote) obj;
                    if (beanRemote != null) {
                        Library lib = new Library(libraryName, status);
                        beanRemote.updateLibrary(libraryId, lib);
                        RequestDispatcher rd = request.getRequestDispatcher(LibraryServlet);
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println(listError);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('" + listError + "');");
                    out.println("location='update-library.jsp';");
                    out.println("</script>");
                    System.out.println(listError);
                }
            } else if (button.equals("DeleteLibrary")) {
                int libraryId = Integer.parseInt(request.getParameter("txtLibraryId"));
                Context context = new InitialContext();
                Object obj = context.lookup("LibJNDI");
                LibrarySessionBeanRemote beanRemote = (LibrarySessionBeanRemote) obj;
                boolean result = beanRemote.DeleteLibrary(libraryId);
                response.sendRedirect(LibraryServlet);
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
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(LibraryProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LibraryProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
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
