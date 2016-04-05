///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package myo.fpt.sample.entity.service;
//
//import javax.naming.NamingException;
//import javax.persistence.EntityManagerFactory;
//import javax.naming.InitialContext;
//import myo.fpt.sample.entity.controller.DictionaryJpaController;
//import myo.fpt.sample.entity.Dictionary;
//import java.net.URI;
//import java.util.List;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Response;
//
///**
// *
// * @author nguyen
// */
//@Path("myo.fpt.sample.entity.dictionary")
//public class DictionaryRESTFacade {
//
//    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
//        return (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence-factory");
//    }
//
//    private DictionaryJpaController getJpaController() {
//        try {
//            return new DictionaryJpaController(getEntityManagerFactory());
//        } catch (NamingException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public DictionaryRESTFacade() {
//    }
//
//    @POST
//    @Consumes({"application/xml", "application/json"})
//    public Response create(Dictionary entity) {
//        try {
//            getJpaController().create(entity);
//            return Response.created(URI.create(entity.getInstructionId().toString())).build();
//        } catch (Exception ex) {
//            return Response.notModified(ex.getMessage()).build();
//        }
//    }
//
//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public Response edit(Dictionary entity) {
//        try {
//            getJpaController().edit(entity);
//            return Response.ok().build();
//        } catch (Exception ex) {
//            return Response.notModified(ex.getMessage()).build();
//        }
//    }
//
//    @DELETE
//    @Path("{id}")
//    public Response remove(@PathParam("id") Integer id) {
//        try {
//            getJpaController().destroy(id);
//            return Response.ok().build();
//        } catch (Exception ex) {
//            return Response.notModified(ex.getMessage()).build();
//        }
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Dictionary find(@PathParam("id") Integer id) {
//        return getJpaController().findDictionary(id);
//    }
//
//    @GET
//    @Produces({"application/xml", "application/json"})
//    public List<Dictionary> findAll() {
//        return getJpaController().findDictionaryEntities();
//    }
//
//    @GET
//    @Path("{max}/{first}")
//    @Produces({"application/xml", "application/json"})
//    public List<Dictionary> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
//        return getJpaController().findDictionaryEntities(max, first);
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String count() {
//        return String.valueOf(getJpaController().getDictionaryCount());
//    }
//    
//}
