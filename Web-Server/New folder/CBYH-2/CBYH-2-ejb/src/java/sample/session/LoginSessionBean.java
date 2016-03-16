/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sample.entity.Account;
import sample.entity.AccountDetail;

/**
 *
 * @author AnhND
 */
@Stateless
public class LoginSessionBean implements LoginSessionBeanLocal, LoginSessionBeanRemote {

    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;

    @Override
    public boolean checkLogin(String username, String password) {
        String jnql = "SELECT a FROM Account a WHERE a.username = :userparam AND a.password = :passparam";
        Query query = em.createQuery(jnql);
        query.setParameter("userparam", username);
        query.setParameter("passparam", password);
        List<Account> result = query.getResultList();
        if (result.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<AccountDetail> getAllAccountDetail() {
        String jnql = "SELECT a FROM AccountDetail a";
        Query query = em.createQuery(jnql);
        List<AccountDetail> listUA = query.getResultList();
        return listUA;
    }

    public int getDetailIdByUsername(String username) {
        String jnql = "SELECT a.detailId FROM Account a WHERE a.username = :userparam";
        Query query = em.createQuery(jnql);
        query.setParameter("userparam", username);
        int detailId = 0;
        try {
            detailId = (Integer) query.getSingleResult();
        } catch (NoResultException nre) {
        }
        return detailId;
    }

    public boolean isAdmin(int detailId) {
        String jnql = "SELECT a.isStaff FROM AccountDetail a WHERE a.detailId = :detailIdparam";
        Query query = em.createQuery(jnql);
        query.setParameter("detailIdparam", detailId);
        boolean isStaff = true;
        try {
            isStaff = (Boolean) query.getSingleResult();
        } catch (NoResultException nre) {
        }
        if (isStaff == true) {
            return true;
        }
        return false;
    }

    public boolean isActive(int detailId) {
        String jnql = "SELECT a.status FROM AccountDetail a WHERE a.detailId = :detailIdparam";
        Query query = em.createQuery(jnql);
        query.setParameter("detailIdparam", detailId);
        boolean status = true;
        try {
            status = (Boolean) query.getSingleResult();
        } catch (NoResultException nre) {
        }
        if (status == true) {
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
