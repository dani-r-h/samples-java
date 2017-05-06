package com.drodriguezhdez.samples.quartzwithakka.domain;


public class EventScheduler<T extends Event> {

	private final EventSchedulerConfiguration<T> config;
	
	public EventScheduler(final EventSchedulerConfiguration<T> config) {
		this.config = config;
	}
	
	public void start(){
		config.runStartupLogic();
	}
	
	public void shutdown(){
		config.runShutdownLogic();
	}

	public void schedule(final T event) {
		config.runSchedule(event);
	}

}
