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
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import sample.entity.AccountDetail;
import sample.entity.Notification;
import sample.session.NotificationSessionBeanRemote;

/**
 *
 * @author AnhND
 */
public class MyJob implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        JobDataMap map = context.getMergedJobDataMap();
        Object obj = map.get("abc");
        NotificationSessionBeanRemote remote = (NotificationSessionBeanRemote) obj;
        List<Date> result = remote.getExpiredDate();
        System.out.println("Scheduler has started....");
        if (result != null) {
            for (int i = 0; i < result.size(); i++) {
                Date date = result.get(i);
                Date now = new Date();
                if (date != null) {
                    //convert expiration date and now date to Millisecond and calculate
                    List<Integer> listdetailId = remote.getDetailId();
                    int detailId = listdetailId.get(i);
                    Date notiDate = new Date();
                    int custId = remote.findCustIdByDetailId(detailId);
                    boolean isSent = false;
                    int notiId = remote.getNotiId();
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
                        String deviceId = remote.findDeviceIdByCustId(custId);

                        System.out.println("Gan het han");
                        System.out.println("Detail ID " + detailId);
                        String notiContent = "Your account is about to expire. Expiration date: " + date;
                        Notification noti = new Notification(notiId, notiDate, custId, isSent, notiContent, deviceId);
                        remote.createNotification(noti);
                    } else if (diffMinutes < 0) {
                        //Update license type to "basic" and delete expiration date
                        String deviceId = remote.findDeviceIdByCustId(custId);

                        String licenseType = "basic";
                        Date expiredDate = null;
                        String notiContent = "Your license is overdate.";
                        System.out.println("Het han roi ban oi");
                        System.out.println("Detail ID " + detailId);
                        Notification noti = new Notification(notiId, notiDate, custId, isSent, notiContent, deviceId);
                        remote.createNotification(noti);
                        AccountDetail AccDetailUpdate = new AccountDetail(licenseType, expiredDate);
                        remote.updateOverExpiredDate(detailId, AccDetailUpdate);
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
}
