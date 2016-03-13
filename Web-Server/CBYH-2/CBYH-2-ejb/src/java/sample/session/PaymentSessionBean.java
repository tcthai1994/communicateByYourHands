/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sample.entity.AccountDetail;
import sample.entity.Recipt;

/**
 *
 * @author AnhND
 */
@Stateless
public class PaymentSessionBean implements PaymentSessionBeanLocal , PaymentSessionBeanRemote{
    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;
    
    public void addRecipt(Recipt recipt){
        em.persist(recipt);
    }
    
    public int getLicenseId(){
        String jnql = "SELECT a.licenseId FROM License a WHERE a.licenseName = :premium";
        Query query = em.createQuery(jnql);
        query.setParameter("premium", "premium");
        int licenseId = (Integer) query.getSingleResult();
        return licenseId;
    }
    
    public double getPrice(){
        String jnql = "SELECT a.price FROM License a WHERE a.licenseName = :premium";
        Query query = em.createQuery(jnql);
        query.setParameter("premium", "premium");
        double price = (Double) query.getSingleResult();
        return price;
    }
    
    public void updateAfterPayment(int detailId, AccountDetail accDetailUpdate){
        AccountDetail accDetail = em.find(AccountDetail.class, detailId);
        accDetail.setLicenseType(accDetailUpdate.getLicenseType());
        accDetail.setExpiredDate(accDetailUpdate.getExpiredDate());
        em.merge(accDetail);
    }
    
    public int findDetailIdByCustId(int custId){
        String jnql = "SELECT a.detailId FROM Account a WHERE a.custId = :custIdparam";
        Query query = em.createQuery(jnql);
        query.setParameter("custIdparam", custId);
        return (Integer) query.getSingleResult();
    }
    
    public Date findExpiredDateByDetailId(int detailId){
        String jnql = "SELECT a.expiredDate FROM AccountDetail a WHERE a.detailId = :detailIdparam";
        Query query = em.createQuery(jnql);
        query.setParameter("detailIdparam", detailId);
        Date expiredDate = (Date) query.getSingleResult();
        return expiredDate;
    }


    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
