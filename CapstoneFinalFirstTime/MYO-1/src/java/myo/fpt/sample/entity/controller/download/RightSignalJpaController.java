/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller.download;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myo.fpt.sample.entity.RightSignal;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class RightSignalJpaController implements Serializable {

    public RightSignalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RightSignal rightSignal) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rightSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRightSignal(rightSignal.getEmgCode()) != null) {
                throw new PreexistingEntityException("RightSignal " + rightSignal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RightSignal rightSignal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rightSignal = em.merge(rightSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = rightSignal.getEmgCode();
                if (findRightSignal(id) == null) {
                    throw new NonexistentEntityException("The rightSignal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RightSignal rightSignal;
            try {
                rightSignal = em.getReference(RightSignal.class, id);
                rightSignal.getEmgCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rightSignal with id " + id + " no longer exists.", enfe);
            }
            em.remove(rightSignal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RightSignal> findRightSignalEntities() {
        return findRightSignalEntities(true, -1, -1);
    }

    public List<RightSignal> findRightSignalEntities(int maxResults, int firstResult) {
        return findRightSignalEntities(false, maxResults, firstResult);
    }

    private List<RightSignal> findRightSignalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RightSignal.class));
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

    public RightSignal findRightSignal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RightSignal.class, id);
        } finally {
            em.close();
        }
    }

    public int getRightSignalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RightSignal> rt = cq.from(RightSignal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
