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
import myo.fpt.sample.entity.Library;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class LibraryJpaController implements Serializable {

    public LibraryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Library> getAllLibrary() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM Library a";
            Query query = em.createQuery(jnql);
            List<Library> listLib = query.getResultList();
            return listLib;
        } finally {
            em.close();
        }
    }

    public void addNewLibrary(Library lib) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(lib);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateLibrary(int libraryId, Library libUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Library lib = em.find(Library.class, libraryId);
            lib.setLibraryName(libUpdate.getLibraryName());
            lib.setStatus(libUpdate.getStatus());
            em.merge(lib);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean DeleteLibrary(int libraryId) {
        EntityManager em = getEntityManager();
        try {
            Library lib = em.find(Library.class, libraryId);
            if (lib != null) {
                em.getTransaction().begin();
                em.remove(lib);
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
