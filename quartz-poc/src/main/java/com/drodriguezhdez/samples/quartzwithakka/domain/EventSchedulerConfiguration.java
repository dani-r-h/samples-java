package com.drodriguezhdez.samples.quartzwithakka.domain;


public abstract class EventSchedulerConfiguration<T> {
	
	public abstract void runStartupLogic();

	public abstract void runShutdownLogic();

	public abstract void runSchedule(final T event);
}
