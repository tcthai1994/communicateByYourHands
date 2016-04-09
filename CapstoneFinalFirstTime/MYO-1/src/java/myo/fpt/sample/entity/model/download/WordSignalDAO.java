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
import myo.fpt.sample.entity.WordSignal;
import myo.fpt.sample.entity.WordSignalPK;
import myo.fpt.sample.entity.model.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.model.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class WordSignalDAO implements Serializable {

    public WordSignalDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WordSignal wordSignal) throws PreexistingEntityException, Exception {
        if (wordSignal.getWordSignalPK() == null) {
            wordSignal.setWordSignalPK(new WordSignalPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(wordSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWordSignal(wordSignal.getWordSignalPK()) != null) {
                throw new PreexistingEntityException("WordSignal " + wordSignal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WordSignal wordSignal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            wordSignal = em.merge(wordSignal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                WordSignalPK id = wordSignal.getWordSignalPK();
                if (findWordSignal(id) == null) {
                    throw new NonexistentEntityException("The wordSignal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(WordSignalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WordSignal wordSignal;
            try {
                wordSignal = em.getReference(WordSignal.class, id);
                wordSignal.getWordSignalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The wordSignal with id " + id + " no longer exists.", enfe);
            }
            em.remove(wordSignal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<WordSignal> findWordSignalEntities() {
        return findWordSignalEntities(true, -1, -1);
    }

    public List<WordSignal> findWordSignalEntities(int maxResults, int firstResult) {
        return findWordSignalEntities(false, maxResults, firstResult);
    }

    private List<WordSignal> findWordSignalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(WordSignal.class));
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

    public WordSignal findWordSignal(WordSignalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WordSignal.class, id);
        } finally {
            em.close();
        }
    }

    public int getWordSignalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<WordSignal> rt = cq.from(WordSignal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
