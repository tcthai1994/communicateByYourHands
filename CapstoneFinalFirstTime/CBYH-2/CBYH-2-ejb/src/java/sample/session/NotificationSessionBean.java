/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import fpt.myo.entityBean.Account;
import fpt.myo.entityBean.AccountDetail;
import fpt.myo.entityBean.Notification;

/**
 *
 * @author AnhND
 */
@Stateless
public class NotificationSessionBean implements NotificationSessionBeanLocal, NotificationSessionBeanRemote {

    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;

    public List<Date> getExpiredDate() {
        String jnql = "SELECT a.expiredDate FROM AccountDetail a";
        Query query = em.createQuery(jnql);
        List<Date> expiredDate = query.getResultList();
        return expiredDate;
    }

    public void createNotification(Notification noti) {
        em.persist(noti);
    }

    public int getNotiId() {
        String jnql = "SELECT a.notiId FROM Notification a ORDER BY a.notiId DESC";
        Query query = em.createQuery(jnql);
        query.setFirstResult(0);
        query.setMaxResults(1);
        if (!query.getResultList().isEmpty()) {
            int notiId = (Integer) query.getSingleResult();
            return notiId;
        }
        return 0;
    }

    public List<Integer> getDetailId() {
        String jnql = "SELECT a.detailId FROM AccountDetail a";
        Query query = em.createQuery(jnql);
        List<Integer> detailIdList = query.getResultList();
        return detailIdList;
    }

    public List<Notification> findNewNotificationsByCustId(int customerId) {
        String sql = "SELECT a FROM Notification a WHERE a.custId = :custId AND a.isSent = :isSent";
        Query query = em.createQuery(sql);
        query.setParameter("custId", customerId);
        query.setParameter("isSent", false);
        return query.getResultList();
    }

    public void updateIsSentNotification(int notificationId, boolean isSent) {
        Notification notification = em.find(Notification.class, notificationId);
        notification.setIsSent(isSent);
        em.merge(notification);
    }

    public int findCustIdByDetailId(int detailId) {
        String jnql = "SELECT a.custId FROM Account a WHERE a.detailId = :detailIdparam";
        Query query = em.createQuery(jnql);
        query.setParameter("detailIdparam", detailId);
        return (Integer) query.getSingleResult();
    }

    public void updateOverExpiredDate(int detailId, AccountDetail accDetailUpdate) {
        AccountDetail AccDetail = em.find(AccountDetail.class, detailId);
        AccDetail.setLicenseType(accDetailUpdate.getLicenseType());
        AccDetail.setExpiredDate(accDetailUpdate.getExpiredDate());
        em.merge(AccDetail);
    }

    public String findDeviceIdByCustId(int custId) {
        Account tmp = em.find(Account.class, custId);
        if (tmp==null) {
            return "";
        }
        return tmp.getDeviceId().toString();
    }
    
    public List<Notification> findAllNewNotification(){
        String jnql = "SELECT a FROM Notification a WHERE a.isSent = :isSent";
        Query query = em.createQuery(jnql);
        query.setParameter("isSent", false);
        return query.getResultList();
    }
    
    public String findRegIdByDeviceId(String deviceId){
        String jnql = "SELECT a.registrationId FROM Device a WHERE a.deviceId = :deviceId";
        Query query = em.createQuery(jnql);
        query.setParameter("deviceId", deviceId);
        return query.getSingleResult().toString();
    }

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
