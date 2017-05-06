package com.drodriguezhdez.samples.quartzwithakka.quartz;

import com.drodriguezhdez.samples.quartzwithakka.domain.Event;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Scheduler;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuartzEventSchedulerConfigurationTest {

    private QuartzSchedulerHelper quartzHelper;
    private Scheduler quartScheduler;
    private Consumer scheduleLogic;

    private QuartzEventSchedulerConfiguration sut;
    
    @Before
    public void setup(){
        initializeMocks();
        initializeSut(); 
    }

    private void initializeMocks() {
        this.quartScheduler = mock(Scheduler.class);
        this.quartzHelper = mock(QuartzSchedulerHelper.class);
        this.scheduleLogic = mock(Consumer.class);

        when(quartzHelper.getScheduler()).thenReturn(quartScheduler);
    }

    private void initializeSut() {
        this.sut = new QuartzEventSchedulerConfiguration(quartzHelper, scheduleLogic);
    }

    @Test
    public void should_run_startup_logic(){
       //Given

        //When
        this.sut.runStartupLogic();

        //Then
        verify(this.quartzHelper).startup(quartScheduler);
    }

    @Test
    public void should_run_shutdown_logic(){
        //Given

        //When
        this.sut.runShutdownLogic();

        //Then
        verify(this.quartzHelper).shutdown(quartScheduler);
    }

    @Test
    public void should_run_schedule() {
        //Given
        final Event event = mock(Event.class);

        //When
        this.sut.runSchedule(event);

        //Then
        verify(this.quartzHelper).scheduleJob(this.quartScheduler, this.scheduleLogic, event);
    }


}
