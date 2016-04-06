/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller.train;

import fpt.myo.emg.EmgData;
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
 * @author nguyen
 */
public class TrainController implements Serializable {

    public TrainController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int getMeaningLeft() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningLeft FROM MeaningLeft a ORDER BY a.meaningLeft DESC";
            Query query = em.createQuery(jnql);
            if (!query.getResultList().isEmpty()) {
                List<Integer> lMeaningLeft = query.setMaxResults(1).getResultList();
                int meaningLeft = (Integer) lMeaningLeft.get(0);
                return meaningLeft;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public int getMeaningRight() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.meaningRight FROM MeaningRignt a ORDER BY a.meaningRight DESC";
            Query query = em.createQuery(jnql);
            if (!query.getResultList().isEmpty()) {
                List<Integer> lMeaningRight = query.setMaxResults(1).getResultList();
                int meaningRight = (Integer) lMeaningRight.get(0);
                return meaningRight;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public void setMeaningLeft(int meaningLeft) {
        EntityManager em = getEntityManager();
        try {
            MeaningLeft mL = new MeaningLeft(meaningLeft);
            em.getTransaction().begin();
            em.persist(mL);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void setMeaningRight(int meaningRight) {
        EntityManager em = getEntityManager();
        try {
            MeaningRignt mR = new MeaningRignt(meaningRight);
            em.getTransaction().begin();
            em.persist(mR);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void setLeftSignal(String emgCode, int meaningLeft, boolean isCustom) {
        EntityManager em = getEntityManager();
        try {
            LeftSignal lS = new LeftSignal(emgCode, meaningLeft, isCustom);
            em.getTransaction().begin();
            em.persist(lS);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void setRightSignal(String emgCode, int meaningRight, boolean isCustom) {
        EntityManager em = getEntityManager();
        try {
            RightSignal rS = new RightSignal(emgCode, meaningRight, isCustom);
            em.getTransaction().begin();
            em.persist(rS);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public int getMeaningCode(String s) {
        EntityManager em = getEntityManager();
        try {
            // find the meaning is exist or not and return meaning code of meaning
            String jnql = "SELECT d FROM DataContent d WHERE d.meaning = :meaning";
            Query query = em.createQuery(jnql);
            query.setParameter("meaning", s);
            List<DataContent> result = query.getResultList();
            if (!result.isEmpty()) {
                return result.get(0).getMeaningCode();
            }
            // if the meaning is not exist return new meaning code
            jnql = "SELECT a.meaningCode FROM DataContent a ORDER BY a.meaningCode DESC";
            query = em.createQuery(jnql);
            List<Integer> lMeaningCode = query.setMaxResults(1).getResultList();
            int meaningCode = (Integer) lMeaningCode.get(0);
            return meaningCode;

        } finally {
            em.close();
        }
    }

    public boolean isMeaningExist(String s) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT d FROM DataContent d WHERE d.meaning = :meaning";
            Query query = em.createQuery(jnql);
            query.setParameter("meaning", s);
            if (query.getResultList().isEmpty()) {
                return false;
            }
            return true;
        } finally {
            em.close();
        }
    }

    public void setDataContent(DataContent dT) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new DataContent(dT.getMeaning(), dT.getLibraryId()));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void setWordSignal(WordSignal wS) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(wS);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Integer> getMeaningLeftFromMeaningCode(int meaningCode) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT w FROM WordSignal w WHERE w.meaningCode = :meaningCode";
            Query query = em.createQuery(jnql);
            query.setParameter("meaningCode", meaningCode);
            List<WordSignal> lws = query.getResultList();
            List<Integer> lInt = new ArrayList<Integer>();
            for (int i = 0; i < lws.size(); i++) {
                if (lws.get(i).getWordSignalPK().getMeaningLeft() == lws.get(i).getWordSignalPK().getMeaningRight()) {
                    lInt.add(lws.get(i).getWordSignalPK().getMeaningLeft());
                }
            }
            return lInt;
        } finally {
            em.close();
        }
    }

    public boolean isMeaningLeftExits(int mL) {
        EntityManager em = getEntityManager();
        try {
            MeaningLeft mLtmp = em.find(MeaningLeft.class, mL);
            if (mLtmp != null) {
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean isMeaningRightExits(int mR) {
        EntityManager em = getEntityManager();
        try {
            MeaningRignt mRtmp = em.find(MeaningRignt.class, mR);
            if (mRtmp != null) {
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean isKeyWordSignalExits(WordSignalPK wsp) {
        EntityManager em = getEntityManager();
        try {
            WordSignal ws = em.find(WordSignal.class, wsp);
            if (ws != null) {
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean trainOneEmg(String meaning, String leftData, String rightData, String leftMeaning, String rightMeaning) {
        boolean isCustom = false;
        boolean isContinue = true;
        int libraryId = 1;
        EmgData emgTmp = new EmgData();
        emgTmp.setFixLine(leftData);
        leftData = emgTmp.toString();
        emgTmp.setFixLine(rightData);
        rightData = emgTmp.toString();
        if (!isMeaningExist(meaning)) {
            int leftCode = 0;
            int rightCode = 0;
            System.out.println("Insert lan dau");
            DataContent dT = new DataContent(meaning, libraryId);
            System.out.println("dataconten Seted:" + dT.getMeaning());
            setDataContent(dT);
            System.out.println("Datacontent Inserted");
            if (isMeaningExist(leftMeaning)) {
                leftCode = getMeaningCode(leftMeaning);
                if (!isMeaningLeftExits(leftCode)) {
                    setMeaningLeft(leftCode);
                }
                setLeftSignal(leftData, leftCode, isCustom);
            } else {
                isContinue = false;
            }
            if (isMeaningExist(rightMeaning) && isContinue == true) {
                rightCode = getMeaningCode(rightMeaning);
                if (!isMeaningLeftExits(rightCode)) {
                    setMeaningRight(rightCode);
                }
                setRightSignal(rightData, rightCode, isCustom);
            } else {
                isContinue = false;
            }
            if (isContinue == true) {
                WordSignalPK wPk = new WordSignalPK(leftCode, rightCode);
                if (!isKeyWordSignalExits(wPk)) {
                    WordSignal wS = new WordSignal(wPk, getMeaningCode(meaning));
                    setWordSignal(wS);
                }
            }
        } else {
            int leftCode = 0;
            int rightCode = 0;
            System.out.println("Co roi choi moi!!!");
            if (isMeaningExist(leftMeaning)) {
                leftCode = getMeaningCode(leftMeaning);
                if (!isMeaningLeftExits(leftCode)) {
                    setMeaningLeft(leftCode);
                }
                setLeftSignal(leftData, leftCode, isCustom);
            } else {
                isContinue = false;
            }
            if (isMeaningExist(rightMeaning) && isContinue == true) {
                rightCode = getMeaningCode(rightMeaning);
                if (!isMeaningRightExits(rightCode)) {
                    setMeaningRight(rightCode);
                }
                setRightSignal(rightData, rightCode, isCustom);
            } else {
                isContinue = false;
            }
            if (isContinue == true) {
                WordSignalPK wPk = new WordSignalPK(leftCode, rightCode);
                if (!isKeyWordSignalExits(wPk)) {
                    WordSignal wS = new WordSignal(wPk, getMeaningCode(meaning));
                    setWordSignal(wS);
                }
            }
        }
        if (isContinue == true) {
            System.out.println("Done");
            return true;
        }
        return false;
    }
}
