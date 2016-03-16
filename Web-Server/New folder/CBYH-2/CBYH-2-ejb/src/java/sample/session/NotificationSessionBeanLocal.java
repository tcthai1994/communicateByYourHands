/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import sample.entity.Notification;

/**
 *
 * @author AnhND
 */
@Local
public interface NotificationSessionBeanLocal {
    List<Date> getExpiredDate();
    void createNotification(Notification noti);
    int getNotiId();
    List<Integer> getDetailId();
    List<Notification> findNewNotificationsByCustId(int customerId);
    void updateIsSentNotification(int notificationId, boolean isSent);
    void persist(Object object);
}
