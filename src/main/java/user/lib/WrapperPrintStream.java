package user.lib;

import kernel.SystemOperations;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class WrapperPrintStream extends PrintStream {
    public WrapperPrintStream(SystemOperations sysCalls, final int fileDescriptor) {
        super(new SimpleSystemOutputStream(sysCalls, fileDescriptor));
    }

    private static class SimpleSystemOutputStream extends OutputStream {
        private final SystemOperations sysCalls;
        private final int fileDescriptor;

        public SimpleSystemOutputStream(SystemOperations sysCalls, int fileDescriptor) {
            this.sysCalls = sysCalls;
            this.fileDescriptor = fileDescriptor;
        }

        @Override
        public void write(int b) throws IOException {
            sysCalls.write(fileDescriptor, new byte[]{(byte) b});
        }
    }
}
