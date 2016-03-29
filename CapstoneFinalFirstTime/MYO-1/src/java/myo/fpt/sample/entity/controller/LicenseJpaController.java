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
import myo.fpt.sample.entity.License;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class LicenseJpaController implements Serializable {

    public LicenseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(License license) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(license);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLicense(license.getLicenseId()) != null) {
                throw new PreexistingEntityException("License " + license + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(License license) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            license = em.merge(license);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = license.getLicenseId();
                if (findLicense(id) == null) {
                    throw new NonexistentEntityException("The license with id " + id + " no longer exists.");
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
            License license;
            try {
                license = em.getReference(License.class, id);
                license.getLicenseId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The license with id " + id + " no longer exists.", enfe);
            }
            em.remove(license);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<License> findLicenseEntities() {
        return findLicenseEntities(true, -1, -1);
    }

    public List<License> findLicenseEntities(int maxResults, int firstResult) {
        return findLicenseEntities(false, maxResults, firstResult);
    }

    private List<License> findLicenseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(License.class));
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

    public License findLicense(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(License.class, id);
        } finally {
            em.close();
        }
    }

    public int getLicenseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<License> rt = cq.from(License.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
