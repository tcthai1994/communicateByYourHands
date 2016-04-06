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
import myo.fpt.sample.entity.LeftSignal;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class LeftSignalJpaController implements Serializable {

    public LeftSignalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LeftSignal leftSignal) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(leftSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLeftSignal(leftSignal.getEmgCode()) != null) {
                throw new PreexistingEntityException("LeftSignal " + leftSignal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LeftSignal leftSignal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            leftSignal = em.merge(leftSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = leftSignal.getEmgCode();
                if (findLeftSignal(id) == null) {
                    throw new NonexistentEntityException("The leftSignal with id " + id + " no longer exists.");
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
            LeftSignal leftSignal;
            try {
                leftSignal = em.getReference(LeftSignal.class, id);
                leftSignal.getEmgCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The leftSignal with id " + id + " no longer exists.", enfe);
            }
            em.remove(leftSignal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LeftSignal> findLeftSignalEntities() {
        return findLeftSignalEntities(true, -1, -1);
    }

    public List<LeftSignal> findLeftSignalEntities(int maxResults, int firstResult) {
        return findLeftSignalEntities(false, maxResults, firstResult);
    }

    private List<LeftSignal> findLeftSignalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LeftSignal.class));
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

    public LeftSignal findLeftSignal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LeftSignal.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeftSignalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LeftSignal> rt = cq.from(LeftSignal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
