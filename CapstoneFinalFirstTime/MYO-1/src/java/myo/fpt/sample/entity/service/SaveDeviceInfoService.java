/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.service;

import fpt.myo.emg.Utils;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.PathSegment;
import myo.fpt.sample.entity.WordSignalPK;
import javax.naming.InitialContext;
import myo.fpt.sample.entity.controller.download.WordSignalJpaController;
import myo.fpt.sample.entity.WordSignal;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import myo.fpt.sample.entity.Device;
import myo.fpt.sample.entity.controller.staff.DeviceJpaController;
import myo.fpt.sample.entity.controller.train.TrainController;
import org.json.simple.JSONObject;

/**
 *
 * @author nguyen
 */
@Path("SaveDeviceAPI")
public class SaveDeviceInfoService {

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

    private DeviceJpaController getJpaController() {
        try {
            return new DeviceJpaController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public SaveDeviceInfoService() {
    }

    @GET
    @Path("/SaveDevice")
    @Produces("application/json")
    public String doSave(@QueryParam("deviceId") String deviceId,
            @QueryParam("regId") String regId) {
        boolean checkDeviceId = getJpaController().isDeviceIdExist(deviceId);
            if (!checkDeviceId) {
                Device device = new Device(deviceId, regId);
                getJpaController().addDevice(device);
                JSONObject status = new JSONObject();
                status.put("status", 1);
                return status.toJSONString();
            }
            else{
                getJpaController().updateRegistrationId(deviceId, regId);
                JSONObject status = new JSONObject();
                status.put("status", 1);
                return status.toJSONString();
            }
    }

}