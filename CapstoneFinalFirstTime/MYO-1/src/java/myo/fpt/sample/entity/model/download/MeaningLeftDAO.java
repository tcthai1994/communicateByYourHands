/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.model.download;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myo.fpt.sample.entity.MeaningLeft;
import myo.fpt.sample.entity.model.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.model.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class MeaningLeftDAO implements Serializable {

    public MeaningLeftDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MeaningLeft meaningLeft) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(meaningLeft);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMeaningLeft(meaningLeft.getMeaningLeft()) != null) {
                throw new PreexistingEntityException("MeaningLeft " + meaningLeft + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MeaningLeft meaningLeft) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            meaningLeft = em.merge(meaningLeft);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = meaningLeft.getMeaningLeft();
                if (findMeaningLeft(id) == null) {
                    throw new NonexistentEntityException("The meaningLeft with id " + id + " no longer exists.");
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
            MeaningLeft meaningLeft;
            try {
                meaningLeft = em.getReference(MeaningLeft.class, id);
                meaningLeft.getMeaningLeft();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The meaningLeft with id " + id + " no longer exists.", enfe);
            }
            em.remove(meaningLeft);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MeaningLeft> findMeaningLeftEntities() {
        return findMeaningLeftEntities(true, -1, -1);
    }

    public List<MeaningLeft> findMeaningLeftEntities(int maxResults, int firstResult) {
        return findMeaningLeftEntities(false, maxResults, firstResult);
    }

    private List<MeaningLeft> findMeaningLeftEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MeaningLeft.class));
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

    public MeaningLeft findMeaningLeft(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MeaningLeft.class, id);
        } finally {
            em.close();
        }
    }

    public int getMeaningLeftCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MeaningLeft> rt = cq.from(MeaningLeft.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
