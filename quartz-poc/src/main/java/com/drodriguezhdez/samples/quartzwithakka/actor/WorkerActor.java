package com.drodriguezhdez.samples.quartzwithakka.actor;

import java.util.function.Consumer;

import akka.actor.AbstractActor;

import com.drodriguezhdez.samples.quartzwithakka.domain.Event;

public class WorkerActor extends AbstractActor {
	
	private final Consumer<Event> workerLogic;
	
	public WorkerActor(final Consumer<Event> workerLogic) {
		this.workerLogic = workerLogic;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Event.class, s -> workerLogic.accept(s))
				.build();
	}
}
