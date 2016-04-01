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
import java.io.File;
import java.util.ArrayList;
import javax.persistence.Persistence;
import myo.fpt.sample.entity.MeaningRignt;
import myo.fpt.sample.entity.MeaningLeft;
import myo.fpt.sample.entity.RightSignal;
import myo.fpt.sample.entity.LeftSignal;
import myo.fpt.sample.entity.WordSignal;
import myo.fpt.sample.entity.DataContent;
import myo.fpt.sample.entity.Download;
import myo.fpt.sample.entity.controller.GetDataForMobileController;
import java.io.FileWriter;
import java.io.IOException;
import javax.ws.rs.core.Response;
/*
 * @author Thai
 */

@Path("TranslateAPI")
public class GetDataForMobile {

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

    private GetDataForMobileController getJpaController() {
        try {
            return new GetDataForMobileController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public GetDataForMobile() {
    }

    /**
     * Retrieves representation of an instance of com.app.api.TestAPI
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("/doDownload")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response doDownload() throws IOException {
        Gson gson = new Gson();
        MeaningLeft ML = getJpaController().getMLData();
        MeaningRignt MR = getJpaController().getMRData();
        LeftSignal LS = getJpaController().getLSData();
        RightSignal RS = getJpaController().getRSData();
        WordSignal WS = getJpaController().getWSData();
        DataContent DT = getJpaController().getDTData();

        Download DW = new Download(ML, MR, LS, RS, WS, DT);
        String result = gson.toJson(DW);
        FileWriter file = new FileWriter("D:/study/Chuyen nganh 9/JsonDownload/abc.json");
        try {
            file.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.flush();
            file.close();
        }
        File downFile = new File("D:/study/Chuyen nganh 9/JsonDownload/abc.json");
        return Response.ok(downFile, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + downFile.getName() + "\"") //optional
                .build();
    }
}
