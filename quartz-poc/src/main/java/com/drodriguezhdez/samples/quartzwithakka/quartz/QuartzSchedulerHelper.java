package com.drodriguezhdez.samples.quartzwithakka.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.function.Consumer;

import com.drodriguezhdez.samples.quartzwithakka.domain.Event;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzSchedulerHelper<T extends Event> {
	
	static final String EVENT_KEY = "event";
	static final String LOGIC_KEY = "logic";

	private final Scheduler scheduler;

	public QuartzSchedulerHelper() {
		try {
			final SchedulerFactory schedFact = new StdSchedulerFactory();
			this.scheduler = schedFact.getScheduler();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}

	}

	public void startup(final Scheduler scheduler) {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}

	}

	public void shutdown(final Scheduler scheduler) {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public void scheduleJob(final Scheduler sched, final Consumer<T> schedulerLogic, final T innerEvent) {

		try {
			final JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put(EVENT_KEY, innerEvent);
			jobDataMap.put(LOGIC_KEY, schedulerLogic);

			final JobDetail jobDetail = newJob(QuartzConfigurableJob.class)
					.withIdentity("myJob_" + innerEvent.getId())
					.usingJobData(jobDataMap)
					.build();
			
			final Trigger trigger = newTrigger()
					.withIdentity("myTrigger_" + innerEvent.getId())
					.startAt(innerEvent.getScheduledTime())
					.withSchedule(simpleSchedule())
					.build();

			sched.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}

	}

	public Scheduler getScheduler(){
		return scheduler;
	}
}
