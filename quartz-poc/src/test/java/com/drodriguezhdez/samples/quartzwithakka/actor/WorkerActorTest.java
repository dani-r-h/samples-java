package com.drodriguezhdez.samples.quartzwithakka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.drodriguezhdez.samples.quartzwithakka.domain.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WorkerActorTest {

    private final ActorSystem testActorSystem = ActorSystem.create("testActorSystem");

    private Consumer<Event> logic;

    private TestActorRef<WorkerActor> sut;

    @Before
    public void setup() {
        initializeMocks();
        initializeSut();
    }

    private void initializeMocks() {
        this.logic = mock(Consumer.class);
    }

    private void initializeSut() {
        this.sut = TestActorRef.create(testActorSystem, Props.create(WorkerActor.class, logic));
    }

    @Test
    public void should_execute_the_worker_logic_when_event_is_received() {
        //Given
        final Event event = mock(Event.class);

        //When
        this.sut.tell(event, ActorRef.noSender());

        //Then
        verify(logic).accept(event);
    }


}
