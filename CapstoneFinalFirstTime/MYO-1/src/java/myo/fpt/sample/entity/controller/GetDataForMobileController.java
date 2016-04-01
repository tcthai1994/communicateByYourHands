/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import myo.fpt.sample.entity.DataContent;
import myo.fpt.sample.entity.LeftSignal;
import myo.fpt.sample.entity.MeaningLeft;
import myo.fpt.sample.entity.MeaningRignt;
import myo.fpt.sample.entity.RightSignal;
import myo.fpt.sample.entity.WordSignal;
import myo.fpt.sample.entity.WordSignalPK;

/**
 *
 * @author Thai
 */
public class GetDataForMobileController {

    public GetDataForMobileController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Integer> getAllMeaningLeft() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM MeaningLeft a";
            Query query = em.createQuery(jnql);
            List lisMeaningLeft = query.getResultList();
            return lisMeaningLeft;
        } finally {
            em.close();
        }
    }

    public List<Integer> getAllMeaningRight() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM MeaningRignt a";
            Query query = em.createQuery(jnql);
            List lisMeaningRight = query.getResultList();
            return lisMeaningRight;
        } finally {
            em.close();
        }
    }

    public List<String> getLeftEmg() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.emgCode FROM LeftSignal a";
            Query query = em.createQuery(jnql);
            List lisLeftEmg = query.getResultList();
            return lisLeftEmg;
        } finally {
            em.close();
        }
    }

    public List<Integer> getMeaningLeft() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningLeft FROM LeftSignal a";
            Query query = em.createQuery(jnql);
            List listMeaningLeft = query.getResultList();
            return listMeaningLeft;
        } finally {
            em.close();
        }
    }

    public List getIsCustomLeft() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.isCustom FROM LeftSignal a";
            Query query = em.createQuery(jnql);
            List listIsCustomLeft = query.getResultList();
            return listIsCustomLeft;
        } finally {
            em.close();
        }
    }

    public List<String> getRightEmg() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.emgCode FROM RightSignal a";
            Query query = em.createQuery(jnql);
            List lisRightEmg = query.getResultList();
            return lisRightEmg;
        } finally {
            em.close();
        }
    }

    public List<Integer> getMeaningRight() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningRight FROM RightSignal a";
            Query query = em.createQuery(jnql);
            List listMeaningRight = query.getResultList();
            return listMeaningRight;
        } finally {
            em.close();
        }
    }

    public List getIsCustomRight() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.isCustom FROM RightSignal a";
            Query query = em.createQuery(jnql);
            List listIsCustomRight = query.getResultList();
            return listIsCustomRight;
        } finally {
            em.close();
        }
    }

    public List<Integer> getMeaningLeftFromWS() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.wordSignalPK.meaningLeft FROM WordSignal a";
            Query query = em.createQuery(jnql);
            List listMeaningLeft = query.getResultList();
            return listMeaningLeft;
        } finally {
            em.close();
        }
    }

    public List<Integer> getMeaningRightFromWS() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.wordSignalPK.meaningRight FROM WordSignal a";
            Query query = em.createQuery(jnql);
            List listMeaningRight = query.getResultList();
            return listMeaningRight;
        } finally {
            em.close();
        }
    }

    public List<Integer> getMeaningCodeFromWS() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningCode FROM WordSignal a";
            Query query = em.createQuery(jnql);
            List listMeaningCode = query.getResultList();
            return listMeaningCode;
        } finally {
            em.close();
        }
    }

    public List<Integer> getMeaningCode() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningCode FROM DataContent a";
            Query query = em.createQuery(jnql);
            List listMeaningCode = query.getResultList();
            return listMeaningCode;
        } finally {
            em.close();
        }
    }

    public List<String> getMeaning() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaning FROM DataContent a";
            Query query = em.createQuery(jnql);
            List listMeaning = query.getResultList();
            return listMeaning;
        } finally {
            em.close();
        }
    }

    public List<Integer> getLibraryId() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.libraryId FROM DataContent a";
            Query query = em.createQuery(jnql);
            List listLibraryId = query.getResultList();
            return listLibraryId;
        } finally {
            em.close();
        }
    }

    public MeaningLeft getMLData() {
        MeaningLeft ML = new MeaningLeft();
        List<Integer> listMeaningLeft = getAllMeaningLeft();
        for (int i = 0; i < listMeaningLeft.size(); i++) {
            ML.setMeaningLeft(listMeaningLeft.get(i));
        }
        return ML;
    }

    public MeaningRignt getMRData() {
        MeaningRignt MR = new MeaningRignt();
        List<Integer> listMeaningRight = getAllMeaningRight();
        for (int i = 0; i < listMeaningRight.size(); i++) {
            MR.setMeaningRight(listMeaningRight.get(i));
        }
        return MR;
    }

    public LeftSignal getLSData() {
        LeftSignal LS = new LeftSignal();
        List<String> listLeftEmg = getLeftEmg();
        List<Integer> listMeaningLeft = getMeaningLeft();

        for (int i = 0; i < listLeftEmg.size(); i++) {
            LS.setEmgCode(listLeftEmg.get(i));
            LS.setMeaningLeft(listMeaningLeft.get(i));
            LS.setIsCustom(false);
        }
        return LS;
    }

    public RightSignal getRSData() {
        RightSignal RS = new RightSignal();
        List<String> listRighttEmg = getRightEmg();
        List<Integer> listMeaningRight = getMeaningRight();

        for (int i = 0; i < listRighttEmg.size(); i++) {
            RS.setEmgCode(listRighttEmg.get(i));
            RS.setMeaningRight(listMeaningRight.get(i));
            RS.setIsCustom(false);
        }
        return RS;
    }

    public WordSignal getWSData() {
        WordSignal WS = new WordSignal();
        WordSignalPK WSPK = new WordSignalPK();

        List<Integer> listMeaningLeft = getMeaningLeftFromWS();
        List<Integer> listMeaningRight = getMeaningRightFromWS();
        List<Integer> listMeaningCode = getMeaningCodeFromWS();

        for (int i = 0; i < listMeaningCode.size(); i++) {
            WS.setMeaningCode(listMeaningCode.get(i));
            WSPK.setMeaningLeft(listMeaningLeft.get(i));
            WSPK.setMeaningRight(listMeaningRight.get(i));
        }
        WS.setWordSignalPK(WSPK);
        return WS;
    }

    public DataContent getDTData(){
       DataContent DT = new DataContent();
       
       List <Integer> listMeaningCode = getMeaningCode();
       List <String> listMeaning = getMeaning();
       List <Integer> listLibrariId = getLibraryId();
       
       for(int i = 0; i< listMeaningCode.size(); i++){
           DT.setMeaningCode(listMeaningCode.get(i));
           DT.setMeaning(listMeaning.get(i));
           DT.setLibraryId(listLibrariId.get(i));
       }
       return DT;
   }
    
    
}
