package com.drodriguezhdez.samples.quartzwithakka.quartz;

import java.util.function.Consumer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class QuartzConfigurableJob<T> implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		final T event = (T) context.getMergedJobDataMap().get(QuartzSchedulerHelper.EVENT_KEY);
		final Consumer<T> jobLogic = (Consumer) context.getMergedJobDataMap().get(QuartzSchedulerHelper.LOGIC_KEY);
		
		jobLogic.accept(event);
	} 

}
