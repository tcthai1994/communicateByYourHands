/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nguyen
 */
@Stateless
public class logEditSessionBean implements logEditSessionBeanLocal, logEditSessionBeanRemote {

    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public int getMeaningCodeBaseMeaning(String meaning) {
        String jnql = "SELECT a.meaningCode FROM DataContent a WHERE a.meaning = :meaning";
        Query query = em.createQuery(jnql);
        query.setParameter("meaning", meaning);
        if(!query.getResultList().isEmpty()){
        int meaningCode = (Integer) query.getSingleResult();
        return meaningCode;
        }
        return 0;
    }

    @Override
    public int getMeaningLeftBaseMeaningCode(int meaningCode) {
        String jnql = "SELECT a.wordSignalPK.meaningLeft FROM WordSignal a WHERE a.meaningCode = :meaningCode";
        Query query = em.createQuery(jnql);
        query.setParameter("meaningCode", meaningCode);
        if(!query.getResultList().isEmpty()){
        int meaningLeft = (Integer) query.getSingleResult();
        return meaningLeft;
        }
        return 0;
    }

    @Override
    public int getMeaningRightBaseMeaningCode(int meaningCode) {
        String jnql = "SELECT a.wordSignalPK.meaningRight FROM WordSignal a WHERE a.meaningCode = :meaningCode";
        Query query = em.createQuery(jnql);
        query.setParameter("meaningCode", meaningCode);
        if(!query.getResultList().isEmpty()){
        int meaningRight = (Integer) query.getSingleResult();
        return meaningRight;
        }
        return 0;
    }

}
