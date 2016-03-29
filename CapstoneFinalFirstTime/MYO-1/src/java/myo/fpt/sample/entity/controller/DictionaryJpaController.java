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
import myo.fpt.sample.entity.Dictionary;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class DictionaryJpaController implements Serializable {

    public DictionaryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dictionary dictionary) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dictionary);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDictionary(dictionary.getInstructionId()) != null) {
                throw new PreexistingEntityException("Dictionary " + dictionary + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dictionary dictionary) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dictionary = em.merge(dictionary);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dictionary.getInstructionId();
                if (findDictionary(id) == null) {
                    throw new NonexistentEntityException("The dictionary with id " + id + " no longer exists.");
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
            Dictionary dictionary;
            try {
                dictionary = em.getReference(Dictionary.class, id);
                dictionary.getInstructionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dictionary with id " + id + " no longer exists.", enfe);
            }
            em.remove(dictionary);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dictionary> findDictionaryEntities() {
        return findDictionaryEntities(true, -1, -1);
    }

    public List<Dictionary> findDictionaryEntities(int maxResults, int firstResult) {
        return findDictionaryEntities(false, maxResults, firstResult);
    }

    private List<Dictionary> findDictionaryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dictionary.class));
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

    public Dictionary findDictionary(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dictionary.class, id);
        } finally {
            em.close();
        }
    }

    public int getDictionaryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dictionary> rt = cq.from(Dictionary.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
