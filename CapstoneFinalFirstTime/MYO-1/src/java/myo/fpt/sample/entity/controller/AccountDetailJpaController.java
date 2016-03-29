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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class AccountDetailJpaController implements Serializable {

    public AccountDetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountDetail accountDetail) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(accountDetail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAccountDetail(accountDetail.getDetailId()) != null) {
                throw new PreexistingEntityException("AccountDetail " + accountDetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountDetail accountDetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            accountDetail = em.merge(accountDetail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accountDetail.getDetailId();
                if (findAccountDetail(id) == null) {
                    throw new NonexistentEntityException("The accountDetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AccountDetail accountDetail;
            try {
                accountDetail = em.getReference(AccountDetail.class, id);
                accountDetail.getDetailId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountDetail with id " + id + " no longer exists.", enfe);
            }
            em.remove(accountDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountDetail> findAccountDetailEntities() {
        return findAccountDetailEntities(true, -1, -1);
    }

    public List<AccountDetail> findAccountDetailEntities(int maxResults, int firstResult) {
        return findAccountDetailEntities(false, maxResults, firstResult);
    }

    private List<AccountDetail> findAccountDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountDetail.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AccountDetail findAccountDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountDetail> rt = cq.from(AccountDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
