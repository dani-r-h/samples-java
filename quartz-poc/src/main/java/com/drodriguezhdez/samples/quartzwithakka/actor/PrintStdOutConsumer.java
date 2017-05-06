package com.drodriguezhdez.samples.quartzwithakka.actor;

import com.drodriguezhdez.samples.quartzwithakka.domain.Event;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

public class PrintStdOutConsumer<T extends Event> implements Consumer<T> {

    private final OutputStream outputStream;

    public PrintStdOutConsumer(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void accept(T t) {
        System.setOut(new PrintStream(this.outputStream));
        System.out.print("Event in actor after scheduling!!!: "+ t);
    }
}
