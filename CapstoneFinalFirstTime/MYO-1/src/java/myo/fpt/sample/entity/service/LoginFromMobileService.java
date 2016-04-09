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
import myo.fpt.sample.entity.model.download.WordSignalDAO;
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
import myo.fpt.sample.entity.model.train.TrainDAO;
import myo.fpt.sample.entity.model.detect.TranslateDAO;
import com.google.gson.Gson;
import fpt.myo.emg.EmgData;
import java.util.ArrayList;
import javax.persistence.Persistence;
import javax.ws.rs.QueryParam;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.model.staff.DeviceDAO;
import org.json.simple.JSONObject;

/**
 *
 * @author Thai
 */
@Path("LoginFromMobileAPI")
public class LoginFromMobileService {

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

    private DeviceDAO getJpaController() {
        try {
            return new DeviceDAO(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public LoginFromMobileService() {
    }

    /**
     * Retrieves representation of an instance of com.app.api.TestAPI
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/doLoginFromMobile")
    @Produces("application/json")
    public String doLoginFromMobile(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("deviceId") String deviceId) {
        boolean checkLogin = getJpaController().checkLogin(username, password);
        if (checkLogin) {
            int detailId = getJpaController().getDetailIdByUsername(username);
            Account Acc = getJpaController().getCustIdByUsername(username);
            boolean isActive = getJpaController().isActive(detailId);
            if (isActive) {
                Account account = new Account(deviceId);
                getJpaController().addDeviceIdToAccount(Acc.getCustId(), account);
                getJpaController().updateLogin(deviceId);
                JSONObject status = new JSONObject();
                status.put("status", 1);
                status.put("custId", Acc.getCustId());
                if (Acc.getExpiredDate()!=null) {
                     status.put("expiredDate", Acc.getExpiredDate().getTime());
                } else{
                     status.put("expiredDate", 0);
                }
               
                status.put("isStaff", Acc.getIsStaff());
                return status.toJSONString();
            } else {
                JSONObject status = new JSONObject();
                status.put("status", 0);
                status.put("custId", 0);
                status.put("expiredDate", 0);
                status.put("isStaff", 0);
                return status.toJSONString();
            }
        } else {
            JSONObject status = new JSONObject();
            status.put("status", 0);
            status.put("custId", 0);
            status.put("expiredDate", 0);
            status.put("isStaff", 0);
            return status.toJSONString();
        }
    }

    @POST
    @Path("/doLogOutFromMobile")
    @Produces("application/json")
    public String doLogoutFromMobile(
            @QueryParam("deviceId") String deviceId) {

        getJpaController().updateLogout(deviceId);;
        JSONObject status = new JSONObject();
        status.put("status", 1);
        return status.toJSONString();

    }
}
