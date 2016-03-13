/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sample.entity.Library;

/**
 *
 * @author AnhND
 */
@Stateless
public class LibrarySessionBean implements LibrarySessionBeanLocal , LibrarySessionBeanRemote{
    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;
    
    public List<Library> getAllLibrary(){
        String jnql = "SELECT a FROM Library a";
        Query query = em.createQuery(jnql);
        List<Library> listLib = query.getResultList();
        return listLib;
    }
    
    public void addNewLibrary(Library lib){
        em.persist(lib);
    }
    
    public void updateLibrary(int libraryId, Library libUpdate){
        Library lib = em.find(Library.class, libraryId);
        lib.setLibraryName(libUpdate.getLibraryName());
        lib.setStatus(libUpdate.getStatus());
        em.merge(lib);
    }
    
    public boolean DeleteLibrary(int libraryId){
        Library lib = em.find(Library.class, libraryId);
        if(lib != null ){
            em.remove(lib);
            return true;
        }
        return false;
    }

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
