/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author AnhND
 */
public class MyServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        try {
            //Create & start the scheduler.
            StdSchedulerFactory factory = new StdSchedulerFactory();
            //  factory.initialize(sce.getServletContext().getResourceAsStream("/WEB-INF/quartz.properties"));
            Scheduler scheduler = factory.getScheduler();
            String schedulerTime = "";
            InputStream in = getClass().getClassLoader().getResourceAsStream("..//CheckExpiredDateConfig.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            if ((schedulerTime = br.readLine()) == null) {
                schedulerTime = "0 0 0 * * ?";
            }
            System.out.println(schedulerTime);
            // create job
            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity("JobName", "group1").build();

//                Context context = new InitialContext();
//                Object obj = context.lookup("NotiJNDI");
//                JobDataMap map = new JobDataMap();
//                map.put("abc", obj);
            java.util.Calendar startTime = java.util.Calendar.getInstance();
            startTime.set(java.util.Calendar.HOUR_OF_DAY, 14);
            startTime.set(java.util.Calendar.MINUTE, 45);
            startTime.set(java.util.Calendar.SECOND, 0);
            startTime.set(java.util.Calendar.MILLISECOND, 0);
            if (startTime.getTime().before(new Date())) {
                startTime.add(java.util.Calendar.DAY_OF_MONTH, 1);
                //if the startTime will be before the current time, move to next day
            }

            // create trigger
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("JobName", "group1")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(schedulerTime))
                    .startAt(startTime.getTime())
                    .build();

//            Trigger trigger = TriggerBuilder
//                    .newTrigger()
//                    .withIdentity("dummyTriggerName", "group1")
//                    .usingJobData(map)
//                    .withSchedule(
//                            CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//                    .build();
//            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
//                    .usingJobData(map)
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInSeconds(300)//time repeat(seconds)
//                            .repeatForever())
//                    .startAt(startTime.getTime())
//                    .build();
//            trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//            trigger.setStartTime(startTime.getTime());
//// 24 hours * 60(minutes per hour) * 60(seconds per minute) * 1000(milliseconds per second)
//            trigger.setRepeatInterval(24L * 60L * 60L * 1000L);
            String schedulerTime2 = "";
            InputStream in2 = getClass().getClassLoader().getResourceAsStream("..//SendNotiToMobileConfig.txt");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
            if ((schedulerTime2 = br2.readLine()) == null) {
                schedulerTime2 = "0 0 0 * * ?";
            }
            System.out.println(schedulerTime2);
            JobDetail job2 = JobBuilder.newJob(SendNotiToMobile.class).withIdentity("JobName2", "group2").build();
            Trigger trigger2 = TriggerBuilder
                    .newTrigger()
                    .withIdentity("JobName2", "group2")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(schedulerTime2))
                    .startAt(startTime.getTime())
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            scheduler.scheduleJob(job2, trigger2);
            // and start it off
            scheduler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
