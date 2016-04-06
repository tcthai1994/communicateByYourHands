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
import myo.fpt.sample.entity.MeaningRignt;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class MeaningRigntJpaController implements Serializable {

    public MeaningRigntJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MeaningRignt meaningRignt) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(meaningRignt);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMeaningRignt(meaningRignt.getMeaningRight()) != null) {
                throw new PreexistingEntityException("MeaningRignt " + meaningRignt + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MeaningRignt meaningRignt) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            meaningRignt = em.merge(meaningRignt);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = meaningRignt.getMeaningRight();
                if (findMeaningRignt(id) == null) {
                    throw new NonexistentEntityException("The meaningRignt with id " + id + " no longer exists.");
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
            MeaningRignt meaningRignt;
            try {
                meaningRignt = em.getReference(MeaningRignt.class, id);
                meaningRignt.getMeaningRight();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The meaningRignt with id " + id + " no longer exists.", enfe);
            }
            em.remove(meaningRignt);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MeaningRignt> findMeaningRigntEntities() {
        return findMeaningRigntEntities(true, -1, -1);
    }

    public List<MeaningRignt> findMeaningRigntEntities(int maxResults, int firstResult) {
        return findMeaningRigntEntities(false, maxResults, firstResult);
    }

    private List<MeaningRignt> findMeaningRigntEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MeaningRignt.class));
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

    public MeaningRignt findMeaningRignt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MeaningRignt.class, id);
        } finally {
            em.close();
        }
    }

    public int getMeaningRigntCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MeaningRignt> rt = cq.from(MeaningRignt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
