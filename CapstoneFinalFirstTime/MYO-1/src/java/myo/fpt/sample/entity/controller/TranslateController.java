/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller;

import fpt.myo.emg.EmgData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author nguyen
 */
public class TranslateController implements Serializable {

    private final static Double THRESHOLD = 0.01;
    //private final static Double THRESHOLD = 0.005;
    private Double rDetect_distance;
    private Double lDetect_distance;
    private final int cnt = 7;

    public TranslateController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public double distanceCalculation(EmgData streamData, EmgData compareData) {
        double return_val = streamData.getDistanceFrom(compareData) / streamData.getNorm() / compareData.getNorm();
        return return_val;
    }

    public ArrayList<EmgData> convert(ArrayList emgList) {
        ArrayList<EmgData> dataList = new ArrayList<EmgData>();
        for (int i = 0; i < emgList.size(); i++) {
            String StrEmg = emgList.get(i).toString();
            EmgData data = new EmgData();
            data.setLine(StrEmg);
            dataList.add(data);
        }
        return dataList;
    }

    public String reConvert(EmgData dataList) {
        return dataList.toString();
    }

    public ArrayList<String> getAllRightEmg() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.emgCode FROM RightSignal a";
            Query query = em.createQuery(jnql);
            ArrayList<String> listRightEmg = new ArrayList<>(query.getResultList());
            return listRightEmg;
        } finally {
            em.close();
        }
    }

    public ArrayList<String> getAllLeftEmg() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.emgCode FROM LeftSignal a";
            Query query = em.createQuery(jnql);
            ArrayList<String> listLeftEmg = new ArrayList<>(query.getResultList());
            return listLeftEmg;
        } finally {
            em.close();
        }
    }

    public int getMeaningRight(String emgRight) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT r.meaningRight FROM RightSignal r WHERE r.emgCode = :emgCode";
            Query query = em.createQuery(jnql);
            query.setParameter("emgCode", emgRight);
            if (!query.getResultList().isEmpty()) {
                int meaningRight = (Integer) query.getSingleResult();
                return meaningRight;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public int getMeaningLeft(String emgLeft) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT l.meaningLeft FROM LeftSignal l WHERE l.emgCode = :emgCode";
            Query query = em.createQuery(jnql);
            query.setParameter("emgCode", emgLeft);
            if (!query.getResultList().isEmpty()) {
                int meaningLeft = (Integer) query.getSingleResult();
                return meaningLeft;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public int getMeaningCode(int meaningRight, int meaningLeft) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningCode FROM WordSignal a WHERE a.wordSignalPK.meaningLeft = :meaningLeft "
                    + "AND a.wordSignalPK.meaningRight = :meaningRight";
            Query query = em.createQuery(jnql);
            query.setParameter("meaningLeft", meaningLeft);
            query.setParameter("meaningRight", meaningRight);
            System.out.println("mr " + meaningRight);
            System.out.println("ml " + meaningLeft);
            if (!query.getResultList().isEmpty()) {
                int meaningCode = (Integer) query.getSingleResult();
                return meaningCode;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public String getMeaning(int meaningCode) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaning FROM DataContent a WHERE a.meaningCode = :meaningCode";
            Query query = em.createQuery(jnql);
            query.setParameter("meaningCode", meaningCode);
            if (!query.getResultList().isEmpty()) {
                String meaning = (String) query.getSingleResult();
                return meaning;
            }
            return null;
        } finally {
            em.close();
        }
    }

    public int getMeaningCodeBaseLeft(int meaningLeft) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningCode FROM WordSignal a WHERE a.wordSignalPK.meaningLeft = :meaningLeft";
            Query query = em.createQuery(jnql);
            query.setParameter("meaningLeft", meaningLeft);
            if (!query.getResultList().isEmpty()) {
                int meaningCode = (Integer) query.getSingleResult();
                return meaningCode;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public int getMeaningCodeBaseRight(int meaningRight) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningCode FROM WordSignal a WHERE a.wordSignalPK.meaningRight = :meaningRight";
            Query query = em.createQuery(jnql);
            query.setParameter("meaningRight", meaningRight);
            if (!query.getResultList().isEmpty()) {
                int meaningCode = (Integer) query.getSingleResult();
                return meaningCode;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public ArrayList<String> getTranslateResult(ArrayList<EmgData> leftCompare, ArrayList<EmgData> rightCompare) {
        int meaningCode = 0;
        int meaningCodeLeft = 0;
        int meaningCodeRight = 0;
        EmgData tmpRight = new EmgData();
        EmgData tmpLeft = new EmgData();
        String matchRight = "";
        String matchLeft = "";
        int meaningRight = 0;
        int meaningLeft = 0;
        ArrayList listMatchRight = new ArrayList();
        ArrayList listMatchLeft = new ArrayList();
        ArrayList listMeaningRight = new ArrayList();
        ArrayList listMeaningLeft = new ArrayList();
        ArrayList listMeaningCode = new ArrayList();
        ArrayList listMeaningTmpLeft = new ArrayList();
        ArrayList listMeaningTmpRight = new ArrayList();
        ArrayList listTmpLeft = new ArrayList();
        ArrayList listTmpRight = new ArrayList();
        ArrayList listMeaning = new ArrayList();
        ArrayList<EmgData> rightBase = new ArrayList<EmgData>();
        ArrayList<EmgData> leftBase = new ArrayList<EmgData>();
        ArrayList<EmgData> loglLeftCompare = new ArrayList<EmgData>();
        ArrayList<EmgData> loglRightCompare = new ArrayList<EmgData>();
        ArrayList posLeft = new ArrayList();
        ArrayList posRight = new ArrayList();
        boolean noGuesture = false;
        String meaning = "";

        ArrayList StrRightEmg = getAllRightEmg();
        ArrayList StrLeftEmg = getAllLeftEmg();
        System.out.println("Get data done!!!");

        rightBase = convert(StrRightEmg);
        leftBase = convert(StrLeftEmg);
        System.out.println("Convert done");

        rDetect_distance = THRESHOLD;
        lDetect_distance = THRESHOLD;

        for (int i = 0; i < rightCompare.size(); i++) {
            rDetect_distance = THRESHOLD;
            tmpRight = null;
            for (int j = 0; j < rightBase.size(); j++) {
                double rDistance = distanceCalculation(rightCompare.get(i), rightBase.get(j));
                if (rDistance < rDetect_distance) {
                    rDetect_distance = rDistance;
                    tmpRight = rightBase.get(j);
                }
                if (!(tmpRight == null)) {
                    matchRight = reConvert(tmpRight);
                }
            }
            if (matchRight != null) {
                listMatchRight.add(matchRight);
            }

            lDetect_distance = THRESHOLD;
            tmpLeft = null;
            for (int m = 0; m < leftBase.size(); m++) {
                double lDistance = distanceCalculation(leftCompare.get(i), leftBase.get(m));
                //System.out.println("lD " + lDistance);
                if (lDistance < lDetect_distance) {
                    lDetect_distance = lDistance;
                    tmpLeft = leftBase.get(m);
                }
                if (!(tmpLeft == null)) {
                    matchLeft = reConvert(tmpLeft);
                }
            }
            if (matchLeft != null) {
                listMatchLeft.add(matchLeft);
            }
        }
        System.out.println("Mathching raw ges!!");
        //Find meaningRight
        for (int x = 0; x < listMatchRight.size(); x++) {
            String emgCodeR = listMatchRight.get(x).toString();
            //System.out.println("R:" + emgCodeR);
            meaningRight = getMeaningRight(emgCodeR);
            listMeaningRight.add(meaningRight);
        }
        //Find meaningLeft
        for (int y = 0; y < listMatchLeft.size(); y++) {
            String emgCodeL = listMatchLeft.get(y).toString();
            //System.out.println("L:" + emgCodeL);
            meaningLeft = getMeaningLeft(emgCodeL);
            listMeaningLeft.add(meaningLeft);
        }
        System.out.println("Find meaning each done!!!");
        //Find meaningCode
        for (int a = 0; a < listMeaningRight.size(); a++) {
            meaningCode = 0;
            int mR = Integer.parseInt(listMeaningRight.get(a).toString());
            int mL = Integer.parseInt(listMeaningLeft.get(a).toString());

            meaningCode = getMeaningCode(mR, mL);
            if (meaningCode != 0) {
                listMeaningTmpRight.add(meaningCode);
                listMeaningTmpLeft.add(meaningCode);
            } else {
                if (mR == 0 && mL != 0) {
                    listMeaningTmpRight.add(mL);
                    listMeaningTmpLeft.add(mL);
                } else if (mR != 0 && mL == 0) {
                    listMeaningTmpRight.add(mR);
                    listMeaningTmpLeft.add(mR);
                } else {
                    listMeaningTmpRight.add(mR);
                    listMeaningTmpLeft.add(mL);
                }

            }
        }
        if (true) {
            int cntLeft = 0;
            int cntRight = 0;
            for (int i = 0; i < listMeaningTmpLeft.size() - 1; i++) {
                if (listMeaningTmpLeft.get(i) == listMeaningTmpLeft.get(i + 1)) {
                    cntLeft++;
                    if (cntLeft == cnt) {
                        listTmpLeft.add(listMeaningTmpLeft.get(i));
                        posLeft.add(i);
                    }
                } else {
                    cntLeft = 0;
                }
                if (listMeaningTmpRight.get(i) == listMeaningTmpRight.get(i + 1)) {
                    cntRight++;
                    if (cntRight == cnt) {
                        listTmpRight.add(listMeaningTmpRight.get(i));
                        posRight.add(i);
                    }
                } else {
                    cntRight = 0;
                }
            }
        } else {

        }
        System.out.println("right size " + listTmpRight.size());
        System.out.println("left size " + listTmpLeft.size());
        System.out.println("left win");
        listMeaningCode = listTmpLeft;
        if (!listMeaningCode.isEmpty()) {
            listMeaning.add("");
            for (int b = 0; b < listMeaningCode.size(); b++) {
                int mC = Integer.parseInt(listMeaningCode.get(b).toString());
                meaning = getMeaning(mC);
                if (meaning != null && !meaning.equals(listMeaning.get(listMeaning.size() - 1).toString())) {
                    listMeaning.add(meaning);
                }
            }
        }
        listMeaning.add("******");
        System.out.println("right win");
        listMeaningCode = listTmpRight;
//        listMeaning = new ArrayList();
        if (!listMeaningCode.isEmpty()) {
            for (int b = 0; b < listMeaningCode.size(); b++) {
                int mC = Integer.parseInt(listMeaningCode.get(b).toString());
                meaning = getMeaning(mC);
                if (meaning != null && !meaning.equals(listMeaning.get(listMeaning.size() - 1).toString())) {
                    listMeaning.add(meaning);
                }
            }
        }
        if (!listMeaning.isEmpty()) {
            listMeaning.remove(0);
        }
            System.out.println("I have the meaning!!!!!!!!!!");
            System.out.println("raw left size: " + leftCompare.size());
            System.out.println("raw right size: " + rightCompare.size());
            System.out.println("Find code done!!!");
            System.out.println(listMeaning);

            return listMeaning;
        }
    }