/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller.staff;

import java.io.Serializable;
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
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class DeviceJpaController implements Serializable {

    public DeviceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void addDevice(Device device) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(device);
            em.getTransaction().commit();
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

    public void addDeviceIdToAccount(int custId, Account accountUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Account account = em.find(Account.class, custId);
            account.setDeviceId(accountUpdate.getDeviceId());
            em.merge(account);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean checkLogin(String username, String password) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM Account a WHERE a.username = :userparam AND a.password = :passparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            query.setParameter("passparam", password);
            List<Account> result = query.getResultList();
            if (result.isEmpty()) {
                return false;
            }
            return true;
        } finally {
            em.close();
        }
    }

    public int getDetailIdByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.detailId FROM Account a WHERE a.username = :userparam";
            Query query = em.createQuery(jnql);
            query.setParameter("userparam", username);
            int detailId = 0;
            try {
                detailId = (Integer) query.getSingleResult();
            } catch (NoResultException nre) {
            }
            return detailId;
        } finally {
            em.close();
        }
    }

    public boolean isActive(int detailId) {
        EntityManager em = getEntityManager();
        try {
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
        } finally {
            em.close();
        }
    }

    public boolean isDeviceIdExist(String deviceId) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.deviceId FROM Device a WHERE a.deviceId = :deviceId";
            Query query = em.createQuery(jnql);
            query.setParameter("deviceId", deviceId);
            List<Device> result = query.getResultList();
            if (result.isEmpty()) {
                return false;
            }
            return true;
        } finally {
            em.close();
        }
    }

    public void updateRegistrationId(String deviceId, String regId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Device device = em.find(Device.class, deviceId);
            device.setRegistrationId(regId);
            em.merge(device);
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
}
