package com.drodriguezhdez.samples.quartzwithakka.domain;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventSchedulerTest {

    private EventSchedulerConfiguration config;
    private EventScheduler sut;

    @Before
    public void setup(){
        initializeMocks();
        initializeSut();
    }

    private void initializeMocks() {
        this.config = mock(EventSchedulerConfiguration.class);
    }

    private void initializeSut() {
        this.sut = new EventScheduler(config);
    }

    @Test
    public void should_execute_startup_logic(){
        //Given

        //When
        this.sut.start();

        //Then
        verify(config).runStartupLogic();
    }

    @Test
    public void should_execute_shutdown_logic(){
        //Given

        //When
        this.sut.shutdown();

        //Then
        verify(config).runShutdownLogic();
    }

    @Test
    public void should_execute_schedule_logic(){
        //Given
        final Event event = mock(Event.class);

        //When
        this.sut.schedule(event);

        //Then
        verify(config).runSchedule(event);
    }
}
