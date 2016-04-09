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
import myo.fpt.sample.entity.Dictionary;
import myo.fpt.sample.entity.model.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.model.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class DictionaryDAO implements Serializable {

    public DictionaryDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Dictionary> getAllDictionary() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT d FROM Dictionary d";
            Query query = em.createQuery(jnql);
            List<Dictionary> listDic = query.getResultList();
            return listDic;
        } finally {
            em.close();
        }
    }

    public boolean addNewDictionary(Dictionary dic) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(dic);
            em.getTransaction().commit();
        }
        catch(Exception ex){
            return false;
        }finally {
            em.close();
        }
        return true;
    }

    public boolean updateDictionary(int dictionaryId, Dictionary dicUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Dictionary dictionary = em.find(Dictionary.class, dictionaryId);
            dictionary.setKeyword(dicUpdate.getKeyword());
            dictionary.setDescription(dicUpdate.getDescription());
            dictionary.setVideoURL(dicUpdate.getVideoURL());
            dictionary.setStatus(dicUpdate.getStatus());
            em.merge(dictionary);
            em.getTransaction().commit();
        }
        catch(Exception ex){
            return false;
        }finally {
            em.close();
        }
        return true;
    }

    public boolean deleteDictionary(int dictionaryId) {
        EntityManager em = getEntityManager();
        try {
            Dictionary dictionary = em.find(Dictionary.class, dictionaryId);
            if (dictionary != null) {
                em.getTransaction().begin();
                em.remove(dictionary);
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
