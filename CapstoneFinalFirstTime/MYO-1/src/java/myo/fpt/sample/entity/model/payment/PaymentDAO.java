/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.model.payment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.Device;
import myo.fpt.sample.entity.Recipt;
import myo.fpt.sample.entity.model.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.model.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class PaymentDAO implements Serializable {

    public PaymentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean addRecipt(Recipt recipt) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(recipt);
            em.getTransaction().commit();
        }
        catch(Exception ex){
            return false;
        }finally {
            em.close();
        }
        return true;
    }

    public String getLicenseName() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.licenseName FROM License a WHERE a.licenseId = :premium";
            Query query = em.createQuery(jnql);
            query.setParameter("premium", 2);
            String licenseId = query.getSingleResult().toString();
            return licenseId;
        } finally {
            em.close();
        }
    }

    public double getPrice() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.price FROM License a WHERE a.licenseId = :premium";
            Query query = em.createQuery(jnql);
            query.setParameter("premium", 2);
            double price = (Double) query.getSingleResult();
            return price;
        } finally {
            em.close();
        }
    }

    public boolean updateAfterPayment(int detailId, Account accUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Account acc = em.find(Account.class, detailId);
            acc.setLicenseType(accUpdate.getLicenseType());
            acc.setExpiredDate(accUpdate.getExpiredDate());
            em.merge(acc);
            em.getTransaction().commit();
        }
        catch(Exception ex){
            return false;
        }finally {
            em.close();
        }
        return true;
    }

    public int findDetailIdByCustId(int custId) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.detailId FROM Account a WHERE a.custId = :custIdparam";
            Query query = em.createQuery(jnql);
            query.setParameter("custIdparam", custId);
            return (Integer) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Date findExpiredDateByDetailId(int detailId) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.expiredDate FROM Account a WHERE a.detailId = :detailIdparam";
            Query query = em.createQuery(jnql);
            query.setParameter("detailIdparam", detailId);
            Date expiredDate = (Date) query.getSingleResult();
            return expiredDate;
        } finally {
            em.close();
        }
    }

    public void persist(Object object) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
