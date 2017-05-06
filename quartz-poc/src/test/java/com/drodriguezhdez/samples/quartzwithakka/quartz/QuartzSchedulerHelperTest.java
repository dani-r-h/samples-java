package com.drodriguezhdez.samples.quartzwithakka.quartz;

import com.drodriguezhdez.samples.quartzwithakka.domain.Event;
import org.junit.Before;
import org.junit.Test;
import org.quartz.*;

import java.time.Instant;
import java.util.Date;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzSchedulerHelperTest {

    private static final String SAMPLE_ID = "sampleID";
    private static final String SAMPLE_PAYLOAD = "samplePayload";
    private static final Date SAMPLE_DATE = Date.from(Instant.EPOCH);

    private QuartzSchedulerHelper sut;

    @Before
    public void setup(){
        initializeSut();
    }


    private void initializeSut() {
        this.sut = spy(new QuartzSchedulerHelper());
    }

    @Test
    public void should_invoke_start() throws SchedulerException {
        //Given
        final Scheduler quartScheduler = mock(Scheduler.class);

        //When
        this.sut.startup(quartScheduler);

        //Then
        verify(quartScheduler).start();
    }

    @Test
    public void should_invoke_shutdown() throws SchedulerException {
        //Given
        final Scheduler quartScheduler = mock(Scheduler.class);

        //When
        this.sut.shutdown(quartScheduler);

        //Then
        verify(quartScheduler).shutdown();
    }

    @Test
    public void should_schedule_job() throws SchedulerException {
        //Given
        final Scheduler quartzScheduler = mock(Scheduler.class);
        final Consumer logic = mock(Consumer.class);
        final Event event = new Event(SAMPLE_ID, SAMPLE_PAYLOAD, SAMPLE_DATE);

        //When
        this.sut.scheduleJob(quartzScheduler, logic, event);

        //Then
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(QuartzSchedulerHelper.EVENT_KEY, event);
        jobDataMap.put(QuartzSchedulerHelper.LOGIC_KEY, logic);

        final JobDetail jobDetail = newJob(QuartzConfigurableJob.class)
                .withIdentity("myJob_" + event.getId())
                .usingJobData(jobDataMap)
                .build();

        final Trigger trigger = newTrigger()
                .withIdentity("myTrigger_" + event.getId())
                .startAt(event.getScheduledTime())
                .withSchedule(simpleSchedule())
                .build();

        verify(quartzScheduler).scheduleJob(jobDetail, trigger);
    }

}
