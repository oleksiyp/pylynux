package extension;

import jline.console.ConsoleReader;
import kernel.fs.Channel;

import java.io.IOException;

public class ConsoleChannel extends Channel {
    private ConsoleReader reader;

    public ConsoleChannel(ConsoleReader reader) {
        super();
        this.reader = reader;
    }

    @Override
    public int read(byte[] buf) {
        try {
            return reader.getInput().read(buf);
        } catch (IOException e) {
            throw new RuntimeException("read error", e);
        }
    }

    @Override
    public int write(byte[] buf) {
        try {
            reader.getOutput().write(new String(buf));
            return buf.length;
        } catch (IOException e) {
            throw new RuntimeException("write error", e);
        }
    }
}
