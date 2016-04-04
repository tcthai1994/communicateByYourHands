/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.service;

import fpt.myo.emg.LeftMyoArmband;
import fpt.myo.emg.MyoSignal;
import fpt.myo.emg.Person;
import fpt.myo.emg.RightMyoArmband;
import fpt.myo.emg.Utils;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import myo.fpt.sample.entity.controller.TrainController;
import myo.fpt.sample.entity.controller.TranslateController;
import com.google.gson.Gson;
import fpt.myo.emg.EmgData;
import java.util.ArrayList;
import javax.persistence.Persistence;

/**
 *
 * @author nguyen
 */
@Path("TranslateAPI")
public class TranslateService {

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
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private TranslateController getJpaController() {
        try {
            return new TranslateController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public TranslateService() {
    }

    /**
     * Retrieves representation of an instance of com.app.api.TestAPI
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/doTranslate")
    @Consumes("application/json")
    @Produces("application/json")
    public String doTranslate(String inputData) {
        Gson gson = new Gson();
        MyoSignal myo = gson.fromJson(inputData, MyoSignal.class);
        System.out.println(inputData);
        LeftMyoArmband lMyoArmband = myo.getlEmgJson();
        RightMyoArmband rMyoArmband = myo.getrEmgJson();
        int mode = myo.getMode();
        ArrayList<EmgData> rightCompare = new ArrayList<EmgData>();
        ArrayList<EmgData> leftCompare = new ArrayList<EmgData>();
        leftCompare = lMyoArmband.getLeft();
        rightCompare = rMyoArmband.getRight();
        List<String> ListResult = getJpaController().getTranslateResult(leftCompare, rightCompare, mode);
        String result = gson.toJson(ListResult);
        return result;
    }
}
