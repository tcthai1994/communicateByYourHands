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
import myo.fpt.sample.entity.Recipt;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class ReciptJpaController implements Serializable {

    public ReciptJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recipt recipt) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(recipt);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            if (findRecipt(recipt.getReciptId()) != null) {
                throw new PreexistingEntityException("Recipt " + recipt + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.getTransaction().commit();
                em.close();
            }
        }
    }

    public void edit(Recipt recipt) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            recipt = em.merge(recipt);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recipt.getReciptId();
                if (findRecipt(id) == null) {
                    throw new NonexistentEntityException("The recipt with id " + id + " no longer exists.");
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
            Recipt recipt;
            try {
                recipt = em.getReference(Recipt.class, id);
                recipt.getReciptId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recipt with id " + id + " no longer exists.", enfe);
            }
            em.remove(recipt);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recipt> findReciptEntities() {
        return findReciptEntities(true, -1, -1);
    }

    public List<Recipt> findReciptEntities(int maxResults, int firstResult) {
        return findReciptEntities(false, maxResults, firstResult);
    }

    private List<Recipt> findReciptEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recipt.class));
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

    public Recipt findRecipt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recipt.class, id);
        } finally {
            em.close();
        }
    }

    public int getReciptCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recipt> rt = cq.from(Recipt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
