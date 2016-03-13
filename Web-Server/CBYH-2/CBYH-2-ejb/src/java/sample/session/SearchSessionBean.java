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
public class SearchSessionBean implements SearchSessionBeanLocal , SearchSessionBeanRemote{
    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;
    
    public List<Dictionary> searchByText(String keyword){
        String jnql = "SELECT a FROM Dictionary a WHERE a.keyword LIKE :keywordparam";
        Query query = em.createQuery(jnql);
        query.setParameter("keywordparam", "%"+ keyword+ "%");
        List<Dictionary> listWord = query.getResultList();
        return listWord;
    }

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
