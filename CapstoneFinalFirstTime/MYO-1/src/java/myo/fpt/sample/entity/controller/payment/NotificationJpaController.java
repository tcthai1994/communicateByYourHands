/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.controller.payment;

import java.io.Serializable;
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
import myo.fpt.sample.entity.Notification;
import myo.fpt.sample.entity.controller.exceptions.NonexistentEntityException;
import myo.fpt.sample.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author nguyen
 */
public class NotificationJpaController implements Serializable {

    public NotificationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Date> getExpiredDate() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.expiredDate FROM AccountDetail a";
            Query query = em.createQuery(jnql);
            List<Date> expiredDate = query.getResultList();
            return expiredDate;
        } finally {
            em.close();
        }
    }

    public void createNotification(Notification noti) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(noti);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public int getNotiId() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.notiId FROM Notification a ORDER BY a.notiId DESC";
            Query query = em.createQuery(jnql);
            query.setFirstResult(0);
            query.setMaxResults(1);
            if (!query.getResultList().isEmpty()) {
                int notiId = (Integer) query.getSingleResult();
                return notiId;
            }
            return 0;
        } finally {
            em.close();
        }
    }

    public List<Integer> getDetailId() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.detailId FROM AccountDetail a";
            Query query = em.createQuery(jnql);
            List<Integer> detailIdList = query.getResultList();
            return detailIdList;
        } finally {
            em.close();
        }
    }

    public List<Notification> findNewNotificationsByCustId(int customerId) {
        EntityManager em = getEntityManager();
        try {
            String sql = "SELECT a FROM Notification a WHERE a.custId = :custId AND a.isSent = :isSent";
            Query query = em.createQuery(sql);
            query.setParameter("custId", customerId);
            query.setParameter("isSent", false);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void updateIsSentNotification(int notificationId, boolean isSent) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Notification notification = em.find(Notification.class, notificationId);
            notification.setIsSent(isSent);
            em.merge(notification);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public int findCustIdByDetailId(int detailId) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.custId FROM Account a WHERE a.detailId = :detailIdparam";
            Query query = em.createQuery(jnql);
            query.setParameter("detailIdparam", detailId);
            return (Integer) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public void updateOverExpiredDate(int detailId, Account accDetailUpdate) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Account Acc = em.find(Account.class, detailId);
            Acc.setLicenseType(accDetailUpdate.getLicenseType());
            Acc.setExpiredDate(accDetailUpdate.getExpiredDate());
            em.merge(Acc);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public String findDeviceIdByCustId(int custId) {
        EntityManager em = getEntityManager();
        try {
            Account tmp = em.find(Account.class, custId);
            if (tmp == null) {
                return "";
            }
            return tmp.getDeviceId().toString();
        } finally {
            em.close();
        }
    }

    public List<Notification> findAllNewNotification() {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a FROM Notification a WHERE a.isSent = :isSent";
            Query query = em.createQuery(jnql);
            query.setParameter("isSent", false);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public String findRegIdByDeviceId(String deviceId) {
        EntityManager em = getEntityManager();
        try {
            String jnql = "SELECT a.registrationId FROM Device a WHERE a.deviceId = :deviceId";
            Query query = em.createQuery(jnql);
            query.setParameter("deviceId", deviceId);
            return query.getSingleResult().toString();
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
