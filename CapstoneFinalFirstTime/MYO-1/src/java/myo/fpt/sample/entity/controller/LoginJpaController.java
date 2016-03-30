/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller;

import java.io.Serializable;
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
import myo.fpt.sample.entity.License;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class LoginJpaController implements Serializable {

    public LoginJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean checkLogin(String username, String password) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM Account a WHERE a.username = :userparam AND a.password = :passparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            query.setParameter("passparam", password);
            List<Account> result = query.getResultList();
            if (result.isEmpty()) {
                return false;
            }
            return true;
        } finally {
            em.close();
        }
    }

    public List<AccountDetail> getAllAccountDetail() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM AccountDetail a";
            Query query = em.createQuery(jnql);
            List<AccountDetail> listUA = query.getResultList();
            return listUA;
        } finally {
            em.close();
        }
    }

    public int getDetailIdByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.detailId FROM Account a WHERE a.username = :userparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            int detailId = 0;
            try {
                detailId = (Integer) query.getSingleResult();
            } catch (NoResultException nre) {
            }
            return detailId;
        } finally {
            em.close();
        }
    }

    public boolean isAdmin(int detailId) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.isStaff FROM AccountDetail a WHERE a.detailId = :detailIdparam";
            Query query = em.createQuery(jnql);
            query.setParameter("detailIdparam", detailId);
            boolean isStaff = true;
            try {
                isStaff = (Boolean) query.getSingleResult();
            } catch (NoResultException nre) {
            }
            if (isStaff == true) {
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean isActive(int detailId) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.status FROM AccountDetail a WHERE a.detailId = :detailIdparam";
            Query query = em.createQuery(jnql);
            query.setParameter("detailIdparam", detailId);
            boolean status = true;
            try {
                status = (Boolean) query.getSingleResult();
            } catch (NoResultException nre) {
            }
            if (status == true) {
                return true;
            }
            return false;
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
