/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.gcm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.FileOutputStream;

/**
 *
 * @author AnhND
 */
public class GCMNotificationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Put your Google API Server Key here
    private static final String GOOGLE_SERVER_KEY = "AIzaSyCy8aRC9kcQmRgjbA-DXTQxomZ2v2x5YTM";
    static final String MESSAGE_KEY = "message";

    public GCMNotificationServlet() {
        super();
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
        doPost(request, response);
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
        Result result = null;
        // GCM RedgId of Android device to send push notification
        String regId = "";

            try {
//                BufferedReader br = new BufferedReader(new FileReader(
//                        "GCMRegId.txt"));
//                regId = br.readLine();
//                br.close();
                
                //find new Notification
                
                String userMessage = "";
                Sender sender = new Sender(GOOGLE_SERVER_KEY);
                Message message = new Message.Builder().timeToLive(30)
                        .delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
                System.out.println("regId: " + regId);
                result = sender.send(message, regId, 1);
                request.setAttribute("pushStatus", result.toString());
            } catch (IOException ioe) {
                ioe.printStackTrace();
                request.setAttribute("pushStatus",
                        "RegId required: " + ioe.toString());
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("pushStatus", e.toString());
            }
            request.getRequestDispatcher("GCMTest.jsp")
                    .forward(request, response);
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
