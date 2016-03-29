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
import myo.fpt.sample.entity.CustomContent;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class CustomContentJpaController implements Serializable {

    public CustomContentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CustomContent customContent) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(customContent);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCustomContent(customContent.getMeaningCode()) != null) {
                throw new PreexistingEntityException("CustomContent " + customContent + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CustomContent customContent) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            customContent = em.merge(customContent);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customContent.getMeaningCode();
                if (findCustomContent(id) == null) {
                    throw new NonexistentEntityException("The customContent with id " + id + " no longer exists.");
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
            CustomContent customContent;
            try {
                customContent = em.getReference(CustomContent.class, id);
                customContent.getMeaningCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customContent with id " + id + " no longer exists.", enfe);
            }
            em.remove(customContent);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CustomContent> findCustomContentEntities() {
        return findCustomContentEntities(true, -1, -1);
    }

    public List<CustomContent> findCustomContentEntities(int maxResults, int firstResult) {
        return findCustomContentEntities(false, maxResults, firstResult);
    }

    private List<CustomContent> findCustomContentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CustomContent.class));
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

    public CustomContent findCustomContent(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomContent.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomContentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CustomContent> rt = cq.from(CustomContent.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
