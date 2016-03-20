<%-- 
    Document   : GCMTest
    Created on : Mar 14, 2016, 11:48:38 AM
    Author     : AnhND
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String pushStatus = "";
        Object pushStatusObj = request.getAttribute("pushStatus");

        if (pushStatusObj != null) {
            pushStatus = pushStatusObj.toString();
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Google Cloud Messaging Testing</title>
    </head>
    <body>
        <h1>Google Cloud Messaging (GCM) Server in Java</h1>

        <form action="GCMNotificationServlet" method="post">

            <div>
                <textarea rows="2" name="message" cols="23"
                          placeholder="Message to transmit via GCM"></textarea>
            </div>
            <div>
                <input type="submit" value="Send Push Notification via GCM" />
            </div>
        </form>
        <p>
        <h3>
            <%=pushStatus%>
        </h3>
    </p>
</body>
</html>
