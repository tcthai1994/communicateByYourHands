/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.restFul;

import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import sample.session.LoginSessionBeanRemote;

/**
 * REST Web Service
 *
 * @author AnhND
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of sample.restFul.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }

    @Path("login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String checkLogin(@QueryParam("username") String username, @QueryParam("password") String password) throws NamingException {
        LoginSessionBeanRemote remote = null;
        javax.naming.Context context = new InitialContext();
        Object obj = context.lookup("LoginJNDI");
        remote = (LoginSessionBeanRemote) obj;
        boolean result = remote.checkLogin(username, password);
        return result == true ? "Accepted" : "Not Accepted";
    }
}
