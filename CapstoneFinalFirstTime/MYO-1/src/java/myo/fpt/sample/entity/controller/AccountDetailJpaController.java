/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;
import sample.dto.AccountManage;

/**
 *
 * @author nguyen
 */
public class AccountDetailJpaController implements Serializable {

    public AccountDetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Account> getAllAccount() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            String jnql = "SELECT a FROM Account a";
            Query query = em.createQuery(jnql);
            List<Account> listUA = query.getResultList();
            return listUA;
        } finally {
            em.close();
        }
    }

    public List<AccountDetail> getAllAccountDetail() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM AccountDetail a";
            Query query = em.createQuery(jnql);
            List<AccountDetail> listUAD = query.getResultList();
            return listUAD;
        } finally {
            em.close();
        }
    }

    public void RegistertoAccount(Account Acc) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(Acc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void RegistertoAccountDetail(AccountDetail AccDetail) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(AccDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getDetailId() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.detailId FROM AccountDetail a ORDER BY a.detailId DESC";
            Query query = em.createQuery(jnql);
            query.setFirstResult(0);
            query.setMaxResults(1);
            if (!query.getResultList().isEmpty()) {
                int detailId = (Integer) query.getSingleResult();
                return detailId;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public AccountDetail getACdetailByDetailId(int detailId) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            AccountDetail accd = em.find(AccountDetail.class, detailId);
            return accd;
        } finally {
            em.close();
        }
    }

    public void UpdateAccount(int custId, Account AccUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Account Acc = em.find(Account.class, custId);
            Acc.setUsername(AccUpdate.getUsername());
            Acc.setPassword(AccUpdate.getPassword());
            em.merge(Acc);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void UpdateAccountDetail(int detailid, AccountDetail AccDetailUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            AccountDetail AccDetail = em.find(AccountDetail.class, detailid);
            AccDetail.setEmail(AccDetailUpdate.getEmail());
            AccDetail.setFullname(AccDetailUpdate.getFullname());
            AccDetail.setPhone(AccDetailUpdate.getPhone());
            AccDetail.setIsStaff(AccDetailUpdate.getIsStaff());
            AccDetail.setLicenseType(AccDetailUpdate.getLicenseType());
            AccDetail.setExpiredDate(AccDetailUpdate.getExpiredDate());
            AccDetail.setStatus(AccDetailUpdate.getStatus());
            em.merge(AccDetail);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Account findByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM Account a WHERE a.username = :userparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            return (Account) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public int getCustIdByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.custId FROM Account a WHERE a.username = :userparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            return (Integer) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public boolean checkUsernameExisted(String username) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM Account a WHERE a.username = :userparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            List<Account> result = query.getResultList();
            if (result.isEmpty()) {
                return false;
            }
            return true;
        } finally {
            em.close();
        }
    }

    public boolean checkEmailExisted(String email) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM AccountDetail a WHERE a.email = :emailparam";
            Query query = em.createQuery(jnql);
            query.setParameter("emailparam", email);
            List<Account> result = query.getResultList();
            if (result.isEmpty()) {
                return false;
            }
            return true;
        } finally {
            em.close();
        }
    }

    public void updateExpiredDate(int detailId, AccountDetail accDetailUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            AccountDetail accDetail = em.find(AccountDetail.class, detailId);
            accDetail.setLicenseType(accDetailUpdate.getLicenseType());
            accDetail.setExpiredDate(accDetailUpdate.getExpiredDate());
            em.merge(accDetail);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public int findDetailIdByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.detailId FROM Account a WHERE a.username = :userparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            return (Integer) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public void UserUpdateAccountDetail(int detailid, AccountDetail AccDetailUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            AccountDetail AccDetail = em.find(AccountDetail.class, detailid);
            AccDetail.setEmail(AccDetailUpdate.getEmail());
            AccDetail.setFullname(AccDetailUpdate.getFullname());
            AccDetail.setPhone(AccDetailUpdate.getPhone());
            em.merge(AccDetail);
            em.getTransaction().commit();
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

    public AccountManage getAccMng(String username) {
        EntityManager em = getEntityManager();
        try {
            Account acc = findByUsername(username);
            AccountDetail accDt = getACdetailByDetailId(acc.getDetailId());
            Date date = accDt.getExpiredDate();
            String exDate = "";
            if (date != null) {
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                exDate = ft.format(date);
            }
            AccountManage accTmp = new AccountManage(username, acc.getPassword(), accDt.getDetailId(), accDt.getEmail(), accDt.getFullname(), accDt.getPhone(), accDt.getLicenseType(), exDate);
            return accTmp;
        } finally {
            em.close();
        }
    }

}
