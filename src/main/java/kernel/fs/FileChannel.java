package kernel.fs;

public class FileChannel extends Channel {
    private final File file;
    private boolean readAccess;
    private boolean writeAccess;
    private int position;


    public FileChannel(File file, boolean readAccess, boolean writeAccess) {
        this.file = file;
        this.readAccess = readAccess;
        this.writeAccess = writeAccess;
    }

    public int getPosition() {
        return position;
    }

    public void seek(int position) {
        this.position = position;
    }

    @Override
    public int read(byte[] buf) {
        if (!readAccess) {
            throw new RuntimeException("no access");
        }
        int cnt = file.readContent(buf, position, buf.length);
        position += cnt;
        return cnt;
    }

    @Override
    public int write(byte[] buf) {
        if (!writeAccess) {
            throw new RuntimeException("no access");
        }
        int cnt = file.writeContent(buf, position, buf.length);
        position += cnt;
        return cnt;
    }
}
