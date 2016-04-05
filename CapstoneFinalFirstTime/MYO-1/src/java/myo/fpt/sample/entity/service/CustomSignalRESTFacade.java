/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.service;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.PathSegment;
import myo.fpt.sample.entity.CustomSignalPK;
import javax.naming.InitialContext;
import myo.fpt.sample.entity.controller.CustomSignalJpaController;
import myo.fpt.sample.entity.CustomSignal;
import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author nguyen
 */
@Path("myo.fpt.sample.entity.customsignal")
public class CustomSignalRESTFacade {

    private CustomSignalPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;customLeft=customLeftValue;customRigh=customRighValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        myo.fpt.sample.entity.CustomSignalPK key = new myo.fpt.sample.entity.CustomSignalPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> customLeft = map.get("customLeft");
        if (customLeft != null && !customLeft.isEmpty()) {
            key.setCustomLeft(new java.lang.Integer(customLeft.get(0)));
        }
        java.util.List<String> customRigh = map.get("customRigh");
        if (customRigh != null && !customRigh.isEmpty()) {
            key.setCustomRigh(new java.lang.Integer(customRigh.get(0)));
        }
        return key;
    }

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private CustomSignalJpaController getJpaController() {
        try {
            return new CustomSignalJpaController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public CustomSignalRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(CustomSignal entity) {
        try {
            getJpaController().create(entity);
            return Response.created(URI.create(entity.getCustomSignalPK().getCustomLeft() + "," + entity.getCustomSignalPK().getCustomRigh())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(CustomSignal entity) {
        try {
            getJpaController().edit(entity);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") PathSegment id) {
        try {
            myo.fpt.sample.entity.CustomSignalPK key = getPrimaryKey(id);
            getJpaController().destroy(key);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public CustomSignal find(@PathParam("id") PathSegment id) {
        myo.fpt.sample.entity.CustomSignalPK key = getPrimaryKey(id);
        return getJpaController().findCustomSignal(key);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<CustomSignal> findAll() {
        return getJpaController().findCustomSignalEntities();
    }

    @GET
    @Path("{max}/{first}")
    @Produces({"application/xml", "application/json"})
    public List<CustomSignal> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return getJpaController().findCustomSignalEntities(max, first);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        return String.valueOf(getJpaController().getCustomSignalCount());
    }
    
}
