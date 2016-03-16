/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import sample.entity.AccountDetail;
import sample.entity.Notification;

/**
 *
 * @author AnhND
 */
@Remote
public interface NotificationSessionBeanRemote {
    List<Date> getExpiredDate();
    void createNotification(Notification noti);
    int getNotiId();
    List<Integer> getDetailId();
    List<Notification> findNewNotificationsByCustId(int customerId);
    void updateIsSentNotification(int notificationId, boolean isSent);
    void persist(Object object);
    int findCustIdByDetailId(int detailId);
    void updateOverExpiredDate(int detailId, AccountDetail accDetailUpdate);
    String findDeviceIdByCustId(int custId);
    List<Notification> findAllNewNotification();
    String findRegIdByDeviceId(String deviceId);
}
