package com.drodriguezhdez.samples.quartzwithakka.quartz;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuartzConfigurableJobTest {

    private JobDataMap jobDataMap;

    private QuartzConfigurableJob sut;
    private String serializableObj;
    private Consumer jobLogic;

    @Before
    public void setup() {
        initializeMocks();
        initializeSut();
    }

    private void initializeMocks() {
        this.serializableObj = "";
        this.jobDataMap = mock(JobDataMap.class);
        this.jobLogic = mock(Consumer.class);
    }

    private void initializeSut() {
        this.sut = new QuartzConfigurableJob();
    }

    @Test
    public void should_execute_logic_with_kept_object() throws JobExecutionException {
        //Given
        final JobExecutionContext context = mock(JobExecutionContext.class);
        when(context.getMergedJobDataMap()).thenReturn(jobDataMap);
        when(jobDataMap.get(QuartzSchedulerHelper.EVENT_KEY)).thenReturn(serializableObj);
        when(jobDataMap.get(QuartzSchedulerHelper.LOGIC_KEY)).thenReturn(jobLogic);

        //When
        this.sut.execute(context);

        //Then
        verify(jobLogic).accept(serializableObj);
    }
}
