/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.sessionBean;

import fpt.myo.emg.EmgData;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        double return_val = streamData.getDistanceFrom(compareData)/streamData.getNorm()/compareData.getNorm();
        return return_val;
    }
    
    
}
