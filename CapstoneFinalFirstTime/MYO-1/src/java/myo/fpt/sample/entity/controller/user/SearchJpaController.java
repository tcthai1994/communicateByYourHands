/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.Device;
import myo.fpt.sample.entity.Dictionary;
import myo.fpt.sample.entity.Recipt;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class SearchJpaController implements Serializable {

    public SearchJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Dictionary> searchByText(String keyword) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT d FROM Dictionary d WHERE d.keyword LIKE :keywordparam AND d.status = :status";
            Query query = em.createQuery(jnql);
            query.setParameter("keywordparam", "%"+keyword+"%");
            query.setParameter("status", true);
            List<Dictionary> listWord = query.getResultList();
            return listWord;
        } finally {
            em.close();
        }
    }

    public List<Dictionary> getAllDictionary() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT d FROM Dictionary d WHERE d.status = :status";
            Query query = em.createQuery(jnql);
            query.setParameter("status", true);
            List<Dictionary> listDic = query.getResultList();
            return listDic;
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
