package net.m21xx.labs.j2ee;

import java.text.ParseException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import lombok.extern.slf4j.Slf4j;
import net.m21xx.labs.j2ee.quartz.SandboxJob;

@Slf4j
public class SandboxListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("Hello, world!");
		
		try {
			startJobs();
		} catch (SchedulerException | ParseException e) {
			e.printStackTrace();
		}
	}

	private void startJobs() throws SchedulerException, ParseException {
		SchedulerFactory schedFactory = new StdSchedulerFactory();
		Scheduler sched = schedFactory.getScheduler();
		sched.start();
		
		Trigger trigger = new CronTrigger("trigSandbox", "GROUP_TRIGGERS", "*/2 * * ? * *");
		JobDetail job = new SandboxJob();
		
		sched.scheduleJob(job, trigger);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("Bye, world!");
	}

}
