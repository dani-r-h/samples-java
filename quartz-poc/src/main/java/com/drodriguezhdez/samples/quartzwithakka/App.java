package com.drodriguezhdez.samples.quartzwithakka;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.drodriguezhdez.samples.quartzwithakka.actor.PrintStdOutConsumer;
import com.drodriguezhdez.samples.quartzwithakka.actor.WorkerActor;
import com.drodriguezhdez.samples.quartzwithakka.domain.Event;
import com.drodriguezhdez.samples.quartzwithakka.domain.EventScheduler;
import com.drodriguezhdez.samples.quartzwithakka.domain.EventSchedulerConfiguration;
import com.drodriguezhdez.samples.quartzwithakka.quartz.QuartzSchedulerHelper;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;

import com.drodriguezhdez.samples.quartzwithakka.quartz.QuartzEventSchedulerConfiguration;

public class App {
	
	private static final String EVENT_ID = UUID.randomUUID().toString();
	private static final String EVENT_PAYLOAD = "This is a sample payload for the event!";
	
	private final ActorSystem actorSystem = ActorSystem.create("actorSystem");
	private final QuartzSchedulerHelper<Event> quartzHelper = new QuartzSchedulerHelper<>();

	private final OutputStream outContent;

	public App(final OutputStream outContent) {
		this.outContent = outContent;
	}
	
	
	public static void main(String[] args) throws Exception{
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

		final App app = new App(outContent);
		app.execute();
	}
	
	public void execute() throws Exception{

		final ActorRef printActorRef = actorSystem.actorOf(Props.create(WorkerActor.class, new PrintStdOutConsumer<Event>(this.outContent)),"printActor");
		final EventSchedulerConfiguration<Event> config = new QuartzEventSchedulerConfiguration<Event>(quartzHelper,
																	(Consumer & Serializable) (innerEvent) -> 
																			printActorRef.tell(innerEvent, ActorRef.noSender())
																);

		final EventScheduler<Event> eventScheduler = new EventScheduler<Event>(config);
		
		final Event event = new Event(EVENT_ID, EVENT_PAYLOAD, Date.from(Instant.now()));
		eventScheduler.start();
		eventScheduler.schedule(event);
		
		Thread.sleep(1000); //Wait until scheduling had been made
		eventScheduler.shutdown();
		
		final Future<Terminated> fTerminate = actorSystem.terminate();
		Await.result(fTerminate, FiniteDuration.apply(5, TimeUnit.SECONDS));
	}

	private Consumer<Event> getPrintLogic() {
		return (event -> System.out.println("Event in actor after scheduling!!!: "+ event));
	}
}
