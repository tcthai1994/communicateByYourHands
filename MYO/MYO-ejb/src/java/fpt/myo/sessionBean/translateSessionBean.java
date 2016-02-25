/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import fpt.myo.emg.EmgData;
import fpt.myo.entityBean.LeftSignal;
import fpt.myo.entityBean.RightSignal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Thai
 */
@Stateless
public class translateSessionBean implements translateSessionBeanLocal, translateSessionBeanRemote {

    private final static Double THRESHOLD = 0.01;
    @PersistenceContext(unitName = "MYO-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
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

    @Override
    public ArrayList<String> getAllRightEmg() {
        String jnql = "SELECT a.emgCode FROM RightSignal a";
        Query query = em.createQuery(jnql);
        ArrayList<String> listRightEmg = (ArrayList) query.getResultList();
        return listRightEmg;
    }

    @Override
    public ArrayList<String> getAllLeftEmg() {
        String jnql = "SELECT a.emgCode FROM LeftSignal a";
        Query query = em.createQuery(jnql);
        ArrayList<String> listLeftEmg = (ArrayList) query.getResultList();
        return listLeftEmg;
    }

    @Override
    public int getMeaningRight(String emgRight) {
        String jnql = "SELECT r.meaningRight FROM RightSignal r WHERE r.emgCode = :emgCode";
        Query query = em.createQuery(jnql);
        query.setParameter("emgCode", emgRight);
        if(!query.getResultList().isEmpty()){
        int meaningRight = (Integer) query.getSingleResult();
        return meaningRight;
        }
        return 0;
    }

    @Override
    public int getMeaningLeft(String emgLeft) {
        String jnql = "SELECT l.meaningLeft FROM LeftSignal l WHERE l.emgCode = :emgCode";
        Query query = em.createQuery(jnql);
        query.setParameter("emgCode", emgLeft);
        if(!query.getResultList().isEmpty()){
        int meaningLeft = (Integer) query.getSingleResult();
        return meaningLeft;
        }
        return 0;
    }

    @Override
    public int getMeaningCode(int meaningRight, int meaningLeft) {
        String jnql = "SELECT a.meaningCode FROM WordSignal a WHERE a.wordSignalPK.meaningLeft = :meaningLeft "
                + "AND a.wordSignalPK.meaningRight = :meaningRight";
        Query query = em.createQuery(jnql);
        query.setParameter("meaningLeft", meaningLeft);
        query.setParameter("meaningRight", meaningRight);
        System.out.println("mr " + meaningRight);
        System.out.println("ml " + meaningLeft);
        if(!query.getResultList().isEmpty()){
        int meaningCode = (Integer) query.getSingleResult();
        return meaningCode;
        }
        return 0;
    }

    @Override
    public String getMeaning(int meaningCode) {
        String jnql = "SELECT a.meaning FROM DataContent a WHERE a.meaningCode = :meaningCode";
        Query query = em.createQuery(jnql);
        query.setParameter("meaningCode", meaningCode);
        if(!query.getResultList().isEmpty()){
        String meaning = (String) query.getSingleResult();
        return meaning;
        }
        return null;
    }
}
