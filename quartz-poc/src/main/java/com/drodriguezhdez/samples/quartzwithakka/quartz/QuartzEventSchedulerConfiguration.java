package com.drodriguezhdez.samples.quartzwithakka.quartz;

import java.util.function.Consumer;

import com.drodriguezhdez.samples.quartzwithakka.domain.Event;
import com.drodriguezhdez.samples.quartzwithakka.domain.EventSchedulerConfiguration;

public class QuartzEventSchedulerConfiguration<T extends Event> extends EventSchedulerConfiguration<T> {

	private final QuartzSchedulerHelper<T> quartzHelper;
	private final Consumer<T> scheduleLogic;

	public QuartzEventSchedulerConfiguration(final QuartzSchedulerHelper quartzHelper, final Consumer<T> scheduleLogic) {
		this.quartzHelper = quartzHelper;
		this.scheduleLogic = scheduleLogic;
	}

	@Override 
	public void runStartupLogic() {
		this.quartzHelper.startup(this.quartzHelper.getScheduler());
	}

	@Override
	public void runShutdownLogic() { this.quartzHelper.shutdown(this.quartzHelper.getScheduler());}

	@Override
	public void runSchedule(final T event) { this.quartzHelper.scheduleJob(this.quartzHelper.getScheduler(), this.scheduleLogic, event);}

}
