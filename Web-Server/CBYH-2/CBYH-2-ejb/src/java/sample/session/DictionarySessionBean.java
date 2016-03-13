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
import sample.entity.Dictionary;

/**
 *
 * @author AnhND
 */
@Stateless
public class DictionarySessionBean implements DictionarySessionBeanLocal , DictionarySessionBeanRemote{
    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;
    
    public List<Dictionary> getAllDictionary() {
        String jnql = "SELECT d FROM Dictionary d";
        Query query = em.createQuery(jnql);
        List<Dictionary> listDic = query.getResultList();
        return listDic;
    }

    public void addNewDictionary(Dictionary dic) {
        em.persist(dic);
    }

    public void updateDictionary(int dictionaryId, Dictionary dicUpdate) {
        Dictionary dictionary = em.find(Dictionary.class, dictionaryId);
        dictionary.setKeyword(dicUpdate.getKeyword());
        dictionary.setDescription(dicUpdate.getDescription());
        dictionary.setVideoURL(dicUpdate.getVideoURL());
        dictionary.setStatus(dicUpdate.getStatus());
        em.merge(dictionary);
    }

    public boolean deleteDictionary(int dictionaryId) {
        Dictionary dictionary = em.find(Dictionary.class, dictionaryId);
        if (dictionary != null) {
            em.remove(dictionary);
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
