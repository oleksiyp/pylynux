package kernel.fs;

public class ChannelRegistry {
    private Channel[] channels;

    public ChannelRegistry(int size) { channels = new Channel[size]; }

    public void bind(int fd, Channel channel) {
        if (fd < 0 || fd >= channels.length) {
            throw new RuntimeException("file descriptor '" + fd + "' is out of bounds");
        }

        channels[fd] = channel;
    }

    public Channel get(int fd) {
        if (fd < 0 || fd >= channels.length) {
            throw new RuntimeException("file descriptor '" + fd + "' is out of bounds");
        }
        return channels[fd];
    }
}
