package license.utils;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.CronTrigger;
import org.quartz.SchedulerFactory;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import license.model.*;

public class InactiveScheduler {

    static boolean debug = false, activeMail = false;
    static String mail_host = "";
    static Logger logger = LogManager.getLogger(InactiveScheduler.class);
    int month = 8, day = 20, year=2024;
    Date startDate;
    public InactiveScheduler(String date){

	try{
	    //
	    // for cron date is not needed
	    //
	    if(!date.isEmpty()){
		String strArr[] = date.split("/");
		month = Integer.parseInt(strArr[0]);
		day = Integer.parseInt(strArr[1]);
		year = Integer.parseInt(strArr[2]);
		Calendar cal = new GregorianCalendar();
		cal.set(year, (month-1), day);
		cal.set(Calendar.HOUR_OF_DAY, 6);//to run at 6am of the specified day
		cal.set(Calendar.MINUTE, 30);
		startDate = cal.getTime();
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	}
    }
	
    public String run() throws Exception {
	String msg = "";
	//
        logger.info("------- Initializing ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        logger.info("------- Initialization Complete -----------");

        // computer a time that is on the next round minute
	//  Date runTime = evenMinuteDate(new Date());

        logger.info("------- Scheduling Job  -------------------");

        // define the job and tie it to our Job class
	try{
	    String jobName = "inactive_emps_"+month+"_"+day+"_"+year;
	    String groupName = "inactive_emps";	    
	    JobDetail job = JobBuilder.newJob(InactiveJob.class)
		.withIdentity(jobName, groupName)
		.build();
	    //
	    // probably we can skip this as we can get envBean from context
	    //

	    // 
	    // Trigger will run at 7am on the speciified date
	    // cron date and time entries (year can be ignored)
	    // second minute hour day-of-month month week-day year
	    // you can use ? no specific value, 0/5 for incrment (every 5 seconds)
	    // * for any value (in minutes mean every minute
	    /*
	      (0 0 9,11,13,15,17 ? * 1-5) // year is empty	      
	      cron format
	      Seconds 0
	      minutes 0
	      hours 9.11.13,15,17 (hours of the day)
	      day of month ? (any day)
	      month * (any month)
	      day of week 1-5 (Monday to Friday)
	      year (optional)
	    */
	    // every day from Mon-Friday, start at 7 am till 5 pm
	    // second minute hours day-of-month month day-of-week
	    // start at 7 am, any day of month, any month, any day of the week
	    // String cronStr = "0 25 13 * * ?"; // for test    
	    String cronStr = "0 0 6 L * ?";

	    CronTrigger trigger = TriggerBuilder.newTrigger()
		.withIdentity(jobName	, groupName)
		.withSchedule(CronScheduleBuilder.cronSchedule(cronStr)
			      .withMisfireHandlingInstructionIgnoreMisfires())
		.build();
	    // Tell quartz to schedule the job using our trigger
	    sched.scheduleJob(job, trigger);
	    //  logger.info(job.getKey() + " will run at: " + runTime);  

	    // Start up the scheduler (nothing can actually run until the 
	    // scheduler has been started)
	    sched.start();
	    /*
	      logger.info("------- Started Scheduler -----------------");

	      // wait long enough so that the scheduler as an opportunity to 
	      // run the job!
	      logger.info("------- Waiting 65 seconds... -------------");
	      try {
	      // wait 65 seconds to show job
	      Thread.sleep(65L * 1000L); 
	      // executing...
	      } catch (Exception e) {
	      }

	      // shut down the scheduler
	      logger.info("------- Shutting Down ---------------------");
	      sched.shutdown(true);
	      logger.info("------- Shutdown Complete -----------------");
	    */
	}catch(Exception ex){
	    logger.error(ex);
	    msg += ex;
	}
	return msg;
    }
	
}
