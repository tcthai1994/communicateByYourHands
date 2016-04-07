/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import myo.fpt.sample.entity.Account;
import myo.fpt.sample.entity.AccountDetail;
import myo.fpt.sample.entity.Notification;
import myo.fpt.sample.entity.controller.payment.NotificationJpaController;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author AnhND
 */
public class MyJob implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

//        JobDataMap map = context.getMergedJobDataMap();
//        Object obj = map.get("abc");
        
        List<Date> result = getJpaController().getExpiredDate();
        System.out.println("Scheduler has started....");
        if (result != null) {
            Date now = new Date();
            for (int i = 0; i < result.size(); i++) {
                Date date = result.get(i);
                if (date != null) {
                    //convert expiration date and now date to Millisecond and calculate
                    List<Integer> listdetailId = getJpaController().getDetailId();
                    int detailId = listdetailId.get(i);
                    Date notiDate = new Date();
                    int custId = getJpaController().findCustIdByDetailId(detailId);
                    boolean isSent = false;
                    int notiId = getJpaController().getNotiId();
                    notiId = notiId + 1;
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar1.setTime(now);
                    calendar2.setTime(date);
                    long milliseconds1 = calendar1.getTimeInMillis();
                    long milliseconds2 = calendar2.getTimeInMillis();
                    long diff = milliseconds2 - milliseconds1;
                    long diffMinutes = diff / (60 * 1000);
                    System.out.println(diffMinutes + " Minutes");
                    if (diffMinutes <= 7200 && diffMinutes >= 0) {
                        //check expiration date less than 5 days and create notification
                        System.out.println("custId " + custId);
                        String deviceId = getJpaController().findDeviceIdByCustId(custId);
                        System.out.println("Gan het han");
                        System.out.println("Detail ID " + detailId);
                        String notiContent = "Your account is about to expire. " +'\n' + "Expiration date: " + date;
                        Notification noti = new Notification(notiId, notiDate, custId, isSent, notiContent, deviceId);
                        getJpaController().createNotification(noti);
                    } else if (diffMinutes < 0) {
                        //Update license type to "basic" and delete expiration date
                        String deviceId = getJpaController().findDeviceIdByCustId(custId);
                        String licenseType = "basic";
                        Date expiredDate = null;
                        String notiContent = "Your license is overdate.";
                        System.out.println("Het han roi ban oi");
                        System.out.println("Detail ID " + detailId);
                        Notification noti = new Notification(notiId, notiDate, custId, isSent, notiContent, deviceId);
                        getJpaController().createNotification(noti);
                        Account AccUpdate = new Account(licenseType, expiredDate);
                        getJpaController().updateOverExpiredDate(detailId, AccUpdate);
                    } else {
                        System.out.println("Con xai duoc");
                        System.out.println("Detail ID " + detailId);
                    }
                } else {
                    System.out.println("khong co Expiration date");
                }
            }
        } else {
            System.out.println("list rong");
        }
    }
    
    private EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }
    
    private NotificationJpaController getJpaController() {
        try {
            return new NotificationJpaController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
