/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.scheduler;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import fpt.myo.entityBean.Account;
import fpt.myo.entityBean.Notification;
import sample.session.NotificationSessionBeanRemote;

/**
 *
 * @author AnhND
 */
public class SendNotiToMobile implements Job {

    private static final long serialVersionUID = 1L;

    // Put your Google API Server Key here
    private static final String GOOGLE_SERVER_KEY = "AIzaSyCy8aRC9kcQmRgjbA-DXTQxomZ2v2x5YTM";
    static final String MESSAGE_KEY = "message";

    public void execute(JobExecutionContext context) {
        Result result = null;
        JobDataMap map = context.getMergedJobDataMap();
        Object obj = map.get("abc");
        NotificationSessionBeanRemote remote = (NotificationSessionBeanRemote) obj;
        List<Notification> listNoti = remote.findAllNewNotification();
        if (listNoti != null) {
            for (int i = 0; i < listNoti.size(); i++) {
                
                String userMessage = listNoti.get(i).getNotiContent();
                String deviceId = listNoti.get(i).getDeviceId();
                String regId = remote.findRegIdByDeviceId(deviceId);
                int notiId = listNoti.get(i).getNotiId();
                Sender sender = new Sender(GOOGLE_SERVER_KEY);
                Message message = new Message.Builder().timeToLive(30)
                        .delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
                System.out.println("regId: " + regId);
                try {
                    result = sender.send(message, regId, 1);
                } catch (IOException ex) {
                    Logger.getLogger(SendNotiToMobile.class.getName()).log(Level.SEVERE, null, ex);
                }
                remote.updateIsSentNotification(notiId, true);
            }

        }

    }
}
