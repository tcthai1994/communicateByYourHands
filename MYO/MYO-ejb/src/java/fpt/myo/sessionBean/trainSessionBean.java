/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import fpt.myo.entityBean.MeaningLeft;
import fpt.myo.entityBean.MeaningRignt;
import fpt.myo.entityBean.LeftSignal;
import fpt.myo.entityBean.RightSignal;
import fpt.myo.entityBean.DataContent;
import fpt.myo.entityBean.WordSignal;
import fpt.myo.entityBean.WordSignalPK;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author Thai
 */
@Stateless
public class trainSessionBean implements trainSessionBeanLocal, trainSessionBeanRemote {

    @PersistenceContext(unitName = "MYO-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public int getMeaningLeft() {
        String jnql = "SELECT a.meaningLeft FROM MeaningLeft a ORDER BY a.meaningLeft DESC";
        Query query = em.createQuery(jnql);
        if (!query.getResultList().isEmpty()) {
            List<Integer> lMeaningLeft = query.setMaxResults(1).getResultList();
            int meaningLeft = (Integer) lMeaningLeft.get(0);
            return meaningLeft;
        }
        return 0;
    }

    @Override
    public int getMeaningRight() {
        String jnql = "SELECT a.meaningRight FROM MeaningRignt a ORDER BY a.meaningRight DESC";
        Query query = em.createQuery(jnql);
        if (!query.getResultList().isEmpty()) {
            List<Integer> lMeaningRight = query.setMaxResults(1).getResultList();
            int meaningRight = (Integer) lMeaningRight.get(0);
            return meaningRight;
        }
        return 0;
    }

    @Override
    public void setMeaningLeft(int meaningLeft) {
        MeaningLeft mL = new MeaningLeft(meaningLeft);
        em.persist(mL);
    }

    @Override
    public void setMeaningRight(int meaningRight) {
        MeaningRignt mR = new MeaningRignt(meaningRight);
        em.persist(mR);
    }

    @Override
    public void setLeftSignal(String emgCode, int meaningLeft, boolean isCustom) {
        LeftSignal lS = new LeftSignal(emgCode, meaningLeft, isCustom);
        em.persist(lS);
    }

    @Override
    public void setRightSignal(String emgCode, int meaningRight, boolean isCustom) {
        RightSignal rS = new RightSignal(emgCode, meaningRight, isCustom);
        em.persist(rS);
    }

    @Override
    public int getMeaningCode(String s) {
        // find the meaning is exist or not and return meaning code of meaning
        String jnql = "SELECT d FROM DataContent d WHERE d.meaning = :meaning";
        Query query = em.createQuery(jnql);
        query.setParameter("meaning", s);
        List<DataContent> result = query.getResultList();
        if(!result.isEmpty()){
            return result.get(0).getMeaningCode();
        }
        // if the meaning is not exist return new meaning code
         jnql = "SELECT a.meaningCode FROM DataContent a ORDER BY a.meaningCode DESC";
         query = em.createQuery(jnql);
        List<Integer> lMeaningCode = query.setMaxResults(1).getResultList();
        int meaningCode = (Integer) lMeaningCode.get(0);
        return meaningCode;

    }
    @Override
    public boolean isMeaningExist(String s){
        String jnql = "SELECT d FROM DataContent d WHERE d.meaning = :meaning";
        Query query = em.createQuery(jnql);
        query.setParameter("meaning", s);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void setDataContent(DataContent dT){
        em.persist(dT);
    }
    
    @Override
    public void setWordSignal(WordSignal wS){
        em.persist(wS);
    }
    
    @Override
    public List<Integer> getMeaningLeftFromMeaningCode(int meaningCode){
        String jnql = "SELECT w FROM WordSignal w WHERE w.meaningCode = :meaningCode";
        Query query = em.createQuery(jnql);
        query.setParameter("meaningCode", meaningCode);
        List<WordSignal> lws = query.getResultList();
        List<Integer> lInt = new ArrayList<Integer>();
        for (int i = 0; i < lws.size(); i++) {
            if (lws.get(i).getWordSignalPK().getMeaningLeft()==lws.get(i).getWordSignalPK().getMeaningRight()){
                lInt.add(lws.get(i).getWordSignalPK().getMeaningLeft());
            }
        }
        return lInt;
    }

    public boolean isMeaningLeftExits(int mL) {
        MeaningLeft mLtmp = em.find(MeaningLeft.class, mL);
        if (mLtmp!=null) {
            return true;
        }
        return false;
    }

    public boolean isMeaningRightExits(int mR) {
        MeaningRignt mRtmp = em.find(MeaningRignt.class, mR);
        if (mRtmp!=null) {
            return true;
        }
        return false;
    }

    public boolean isKeyWordSignalExits(WordSignalPK wsp) {
        WordSignal ws = em.find(WordSignal.class, wsp);
        if (ws!=null) {
            return true;
        }
        return false;
    }
}
