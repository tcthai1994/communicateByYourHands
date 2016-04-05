/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.service;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.PathSegment;
import myo.fpt.sample.entity.WordSignalPK;
import javax.naming.InitialContext;
import myo.fpt.sample.entity.controller.WordSignalJpaController;
import myo.fpt.sample.entity.WordSignal;
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
@Path("myo.fpt.sample.entity.wordsignal")
public class WordSignalRESTFacade {

    private WordSignalPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;meaningLeft=meaningLeftValue;meaningRight=meaningRightValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        myo.fpt.sample.entity.WordSignalPK key = new myo.fpt.sample.entity.WordSignalPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> meaningLeft = map.get("meaningLeft");
        if (meaningLeft != null && !meaningLeft.isEmpty()) {
            key.setMeaningLeft(new java.lang.Integer(meaningLeft.get(0)));
        }
        java.util.List<String> meaningRight = map.get("meaningRight");
        if (meaningRight != null && !meaningRight.isEmpty()) {
            key.setMeaningRight(new java.lang.Integer(meaningRight.get(0)));
        }
        return key;
    }

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
    }

    private WordSignalJpaController getJpaController() {
        try {
            return new WordSignalJpaController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public WordSignalRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(WordSignal entity) {
        try {
            getJpaController().create(entity);
            return Response.created(URI.create(entity.getWordSignalPK().getMeaningLeft() + "," + entity.getWordSignalPK().getMeaningRight())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(WordSignal entity) {
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
            myo.fpt.sample.entity.WordSignalPK key = getPrimaryKey(id);
            getJpaController().destroy(key);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public WordSignal find(@PathParam("id") PathSegment id) {
        myo.fpt.sample.entity.WordSignalPK key = getPrimaryKey(id);
        return getJpaController().findWordSignal(key);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<WordSignal> findAll() {
        return getJpaController().findWordSignalEntities();
    }

    @GET
    @Path("{max}/{first}")
    @Produces({"application/xml", "application/json"})
    public List<WordSignal> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return getJpaController().findWordSignalEntities(max, first);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        return String.valueOf(getJpaController().getWordSignalCount());
    }
    
}
