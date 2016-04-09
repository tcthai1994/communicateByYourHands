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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author tanphat199
 */
public class ContextListener implements ServletContextListener {

    private static final String CHECK_DATE_EXPIRE_CONFIGURE_PROPERTIES_PATH = "WEB-INF/CheckExpiredDateConfig.properties";
    private static final String SEND_NOTIFICATION_CONFIGURE_PROPERTIES_PATH = "WEB-INF/SendNotiToMobileConfig.properties";

    private static final String CHECK_EXPIRE_DATE_JOB_NAME = "check_expire_date_job_name";
    private static final String JOB_1_GROUP_NAME = "job_1_group_name";
    private static final String TRIGER_JOB_1_NAME = "triger_job_1_name";

    private static final String SEND_NOTIFICATION_JOB_NAME = "send_notification_job_name";
    private static final String JOB_2_GROUP_NAME = "job_2_group_name";
    private static final String TRIGER_JOB_2_NAME = "triger_job_2_name";

    private Scheduler task1;
    private Scheduler task2;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        String realPath = sce.getServletContext().getRealPath("/");
        System.out.println("Real path: " + realPath);

        /**
         * Read configure file.
         * Format of file: 07 00
         * 07: hour
         * 00: minute
         */
        String schedule = readPropertiesFile(realPath + CHECK_DATE_EXPIRE_CONFIGURE_PROPERTIES_PATH);
        int[] task1ScheduleTime = getHourAndMinuteFromConfigureFile(schedule);
        createSchedule(factory, task1, MyJob.class, CHECK_EXPIRE_DATE_JOB_NAME,
                JOB_1_GROUP_NAME, TRIGER_JOB_1_NAME, task1ScheduleTime[0], task1ScheduleTime[1]);

        schedule = readPropertiesFile(realPath + SEND_NOTIFICATION_CONFIGURE_PROPERTIES_PATH);
        task1ScheduleTime = getHourAndMinuteFromConfigureFile(schedule);
        createSchedule(factory, task2, SendNotiToMobile.class, SEND_NOTIFICATION_JOB_NAME,
                JOB_2_GROUP_NAME, TRIGER_JOB_2_NAME, task1ScheduleTime[0], task1ScheduleTime[1]);
    }

    /**
     * This method using for slit time configure in string into integer[]
     * Example: 00 07 -> integer[] {07, 00}
     * @param schedule
     * @return
     * @throws NumberFormatException 
     */
    private int[] getHourAndMinuteFromConfigureFile(String schedule) throws NumberFormatException {
        String[] scheduleTimes = schedule.split(" ");
        int hour = Integer.parseInt(scheduleTimes[0]);
        int minute = Integer.parseInt(scheduleTimes[1]);
        System.out.println("Hour: " + hour + " | Minute: " + minute);
        return new int[]{hour, minute};
    }

    /**
     * This method using for creating a schedule object
     * @param factory
     * @param taskSchedule
     * @param jobClass
     * @param jobName
     * @param groupName
     * @param triggerName
     * @param hour
     * @param minute 
     */
    private void createSchedule(StdSchedulerFactory factory,
            Scheduler taskSchedule,
            Class jobClass,
            String jobName,
            String groupName,
            String triggerName,
            int hour,
            int minute) {
        try {
            System.out.println("Create schduler for task " + jobClass.getSimpleName());
            //Create & start the scheduler.
            taskSchedule = factory.getScheduler();

            // create job
            JobDetail job = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, groupName).build();

            // create trigger
            Trigger trigger = newTrigger()
                    .withIdentity(triggerName, groupName)
                    .withSchedule(dailyAtHourAndMinute(hour, minute))
                    .forJob(job)
                    .build();

            // Tell quartz to schedule the job using our trigger
            taskSchedule.scheduleJob(job, trigger);
            // and start it off
            taskSchedule.start();
        } catch (SchedulerException ex) {
            System.out.println("Error when create schduler for task " + jobClass.getSimpleName());
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (task1 != null && !task1.isShutdown()) {
                System.out.println("Destroy schedule " + task1.getSchedulerName());
                task1.shutdown();
            }

            if (task2 != null && !task2.isShutdown()) {
                System.out.println("Destroy schedule " + task2.getSchedulerName());
                task2.shutdown();
            }
        } catch (SchedulerException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method using for read content of a file base on file path
     * @param filePath
     * @return 
     */
    private String readPropertiesFile(String filePath) {
        String result = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filePath);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            fileReader.close();
            System.out.print("Contents of file:");
            System.out.println(stringBuffer.toString());

            result = stringBuffer.toString();
        } catch (IOException e) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
}
