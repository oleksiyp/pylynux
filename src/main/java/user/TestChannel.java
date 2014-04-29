package user;

import kernel.fs.Channel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TestChannel extends Channel {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Override
    public int read(byte[] buf) {
        return 0;
    }

    @Override
    public int write(byte[] buf) {
        try {
            output.write(buf);
            return buf.length;
        } catch (IOException e) {
            throw new RuntimeException("byte buffer write error", e);
        }
    }

    public byte[] getOutput() {
        return output.toByteArray();
    }
}
