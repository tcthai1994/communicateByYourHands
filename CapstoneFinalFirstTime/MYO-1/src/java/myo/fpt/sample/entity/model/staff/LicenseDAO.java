/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.model.staff;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myo.fpt.sample.entity.License;
import myo.fpt.sample.entity.model.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.model.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class LicenseDAO implements Serializable {

    public LicenseDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<License> getAllLicense() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM License a";
            Query query = em.createQuery(jnql);
            List<License> listLicense = query.getResultList();
            return listLicense;
        } finally {
            em.close();
        }
    }

    public void addNewLicense(License license) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(license);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean updateLicense(int licenseId, License licUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            License lic = em.find(License.class, licenseId);
            lic.setLicenseName(licUpdate.getLicenseName());
            lic.setPrice(licUpdate.getPrice());
            lic.setDescription(licUpdate.getDescription());
            lic.setStatus(licUpdate.getStatus());
            em.merge(lic);
            em.getTransaction().commit();
        }
        catch(Exception ex){
            return false;
        }finally {
            em.close();
        }
        return true;
    }

    public boolean DeleteLicense(int licenseId) {
        EntityManager em = getEntityManager();
        try {
            License license = em.find(License.class, licenseId);
            if (license != null) {
                em.getTransaction().begin();
                em.remove(license);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    public void persist(Object object) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
