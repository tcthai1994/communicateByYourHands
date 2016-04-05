/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.service;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.naming.InitialContext;
import myo.fpt.sample.entity.controller.payment.ReciptJpaController;
import myo.fpt.sample.entity.Recipt;
import java.net.URI;
import java.util.List;
import javax.persistence.Persistence;
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
@Path("myo.fpt.sample.entity.recipt")
public class ReciptRESTFacade {

    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private ReciptJpaController getJpaController() {
        try {
            return new ReciptJpaController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ReciptRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(Recipt entity) {
        try {
            getJpaController().create(entity);
            return Response.created(URI.create(entity.getReciptId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(Recipt entity) {
        try {
            getJpaController().edit(entity);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        try {
            getJpaController().destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Recipt find(@PathParam("id") Integer id) {
        return getJpaController().findRecipt(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Recipt> findAll() {
        return getJpaController().findReciptEntities();
    }

    @GET
    @Path("{max}/{first}")
    @Produces({"application/xml", "application/json"})
    public List<Recipt> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return getJpaController().findReciptEntities(max, first);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        return String.valueOf(getJpaController().getReciptCount());
    }
    
}
