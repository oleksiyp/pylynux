package kernel.fs;

public abstract class Channel {
    public abstract int read(byte[] buf);

    public abstract int write(byte[] buf);
}
