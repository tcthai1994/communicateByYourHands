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
import myo.fpt.sample.entity.CustomSignal;
import myo.fpt.sample.entity.CustomSignalPK;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class CustomSignalJpaController implements Serializable {

    public CustomSignalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CustomSignal customSignal) throws PreexistingEntityException, Exception {
        if (customSignal.getCustomSignalPK() == null) {
            customSignal.setCustomSignalPK(new CustomSignalPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(customSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCustomSignal(customSignal.getCustomSignalPK()) != null) {
                throw new PreexistingEntityException("CustomSignal " + customSignal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CustomSignal customSignal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            customSignal = em.merge(customSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CustomSignalPK id = customSignal.getCustomSignalPK();
                if (findCustomSignal(id) == null) {
                    throw new NonexistentEntityException("The customSignal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CustomSignalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CustomSignal customSignal;
            try {
                customSignal = em.getReference(CustomSignal.class, id);
                customSignal.getCustomSignalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customSignal with id " + id + " no longer exists.", enfe);
            }
            em.remove(customSignal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CustomSignal> findCustomSignalEntities() {
        return findCustomSignalEntities(true, -1, -1);
    }

    public List<CustomSignal> findCustomSignalEntities(int maxResults, int firstResult) {
        return findCustomSignalEntities(false, maxResults, firstResult);
    }

    private List<CustomSignal> findCustomSignalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CustomSignal.class));
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

    public CustomSignal findCustomSignal(CustomSignalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomSignal.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomSignalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CustomSignal> rt = cq.from(CustomSignal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
