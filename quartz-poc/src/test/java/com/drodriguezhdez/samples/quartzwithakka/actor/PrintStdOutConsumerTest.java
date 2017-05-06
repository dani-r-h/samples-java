package com.drodriguezhdez.samples.quartzwithakka.actor;

import com.drodriguezhdez.samples.quartzwithakka.domain.Event;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PrintStdOutConsumerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private PrintStdOutConsumer sut;

    @Before
    public void setup() {
        initializeSut();
    }

    private void initializeSut() {
        this.sut = new PrintStdOutConsumer(outContent);
    }

    @Test
    public void should_print_using_std_out(){
        //Given
        final Event event = mock(Event.class);

        //When
        this.sut.accept(event);

        //Then
        assertThat(outContent.toString()).isEqualTo("Event in actor after scheduling!!!: "+event);
    }
}
