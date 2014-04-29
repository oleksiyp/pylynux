package kernel.fs;

public class File extends FileSystemObject {
    private byte []content;
    private int size;

    File(Directory parent, String name) {
        super(parent, name);
        content = new byte[0];
    }

    public int getSize() {
        return size;
    }

    public void updateContent(byte[] content, int size) {
        updateLastUpdated();
        this.content = content;
        this.size = size;
    }

    public int readContent(byte[] buf, int position, int length) {
        updateLastAccessed();
        int size = getSize();
        if (position >= size) {
            return 0;
        }
        int toRead = Math.min(size - position, buf.length);
        System.arraycopy(content, position, buf, 0, toRead);
        return toRead;
    }

    public int writeContent(byte[] buf, int position, int length) {
        int newFileSize = position + length;
        if (newFileSize > content.length) {
            byte []newContent = new byte[newFileSize + newFileSize >> 1];
            System.arraycopy(content, 0, newContent, 0, content.length);
            content = newContent;
        }
        System.arraycopy(buf, 0, content, position, length);
        size = newFileSize;
        return length;
    }
}
