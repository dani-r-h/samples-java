package com.drodriguezhdez.samples.quartzwithakka;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private App sut;

    @Before
    public void setup() {
        initializeSut();
    }

    private void initializeSut() {
        this.sut = new App(outputStream);
    }

    @Test
    public void should_have_printed_msg_after_scheduling() throws Exception {
        //Given

        //When
        this.sut.execute();

        //Then
        assertThat(outputStream.toString()).contains("Event in actor after scheduling!!!");
    }


}
