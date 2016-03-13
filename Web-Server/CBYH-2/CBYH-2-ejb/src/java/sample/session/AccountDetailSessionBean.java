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
import sample.entity.Account;
import sample.entity.AccountDetail;

/**
 *
 * @author AnhND
 */
@Stateless
public class AccountDetailSessionBean implements AccountDetailSessionBeanLocal , AccountDetailSessionBeanRemote{
    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;

    public List<Account> getAllAccount(){
        String jnql = "SELECT a FROM Account a";
        Query query = em.createQuery(jnql);
        List<Account> listUA = query.getResultList();
        return listUA;
    }

    public List<AccountDetail> getAllAccountDetail() {
        String jnql = "SELECT a FROM AccountDetail a";
        Query query = em.createQuery(jnql);
        List<AccountDetail> listUAD = query.getResultList();
        return listUAD;
    }
    
    public void RegistertoAccount(Account Acc){
        em.persist(Acc);
    }

    public void RegistertoAccountDetail(AccountDetail AccDetail){
        em.persist(AccDetail);
    }
    
    public int getDetailId() {
        String jnql = "SELECT a.detailId FROM AccountDetail a ORDER BY a.detailId DESC";
        Query query = em.createQuery(jnql);
        query.setFirstResult(0);
        query.setMaxResults(1);
        if (!query.getResultList().isEmpty()) {
            int detailId = (Integer) query.getSingleResult();
            return detailId;
        }
        return 0;
    }
    
    public AccountDetail getACdetailByDetailId (int detailId){
        AccountDetail accd = em.find(AccountDetail.class, detailId);
        return accd;
    }
    public void UpdateAccount(int custId, Account AccUpdate){
        Account Acc = em.find(Account.class, custId);
        Acc.setUsername(AccUpdate.getUsername());
        Acc.setPassword(AccUpdate.getPassword());
        em.merge(Acc);
    }
    
    public void UpdateAccountDetail(int detailid, AccountDetail AccDetailUpdate){
        AccountDetail AccDetail = em.find(AccountDetail.class, detailid);
        AccDetail.setEmail(AccDetailUpdate.getEmail());
        AccDetail.setFullname(AccDetailUpdate.getFullname());
        AccDetail.setPhone(AccDetailUpdate.getPhone());
        AccDetail.setIsStaff(AccDetailUpdate.getIsStaff());
        AccDetail.setLicenseType(AccDetailUpdate.getLicenseType());
        AccDetail.setExpiredDate(AccDetailUpdate.getExpiredDate());
        AccDetail.setStatus(AccDetailUpdate.getStatus());
        em.merge(AccDetail);
    }
    
    public Account findByUsername(String username){
        String jnql = "SELECT a FROM Account a WHERE a.username = :userparam";
        Query query = em.createQuery(jnql);
        query.setParameter("userparam", username);
        return (Account)query.getSingleResult();
    }

    public int getCustIdByUsername(String username){
        String jnql = "SELECT a.custId FROM Account a WHERE a.username = :userparam";
        Query query = em.createQuery(jnql);
        query.setParameter("userparam", username);
        return (Integer) query.getSingleResult();
    }
    
    public boolean checkUsernameExisted(String username){
        String jnql = "SELECT a FROM Account a WHERE a.username = :userparam";
        Query query = em.createQuery(jnql);
        query.setParameter("userparam", username);
        List<Account> result = query.getResultList();
        if(result.isEmpty()){
            return false;
        }
        return true;
    }
    
    public boolean checkEmailExisted(String email){
        String jnql = "SELECT a FROM AccountDetail a WHERE a.email = :emailparam";
        Query query = em.createQuery(jnql);
        query.setParameter("emailparam", email);
        List<Account> result = query.getResultList();
        if(result.isEmpty()){
            return false;
        }
        return true;
    }
    
    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
