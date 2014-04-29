package kernel;

import kernel.fs.Directory;
import kernel.fs.File;
import kernel.fs.FileSystemObject;

import java.util.Date;

public class Stats {
    private final Type type;
    private final Date lastAccessedTime;
    private final int permissions;
    private final Date creationTime;
    private final Date lastUpdatedTime;
    private final int ownerUserId;
    private final int ownerGroupId;
    private final int size;

    public Stats(FileSystemObject fsObject) {
        creationTime = fsObject.getCreationTime();
        lastAccessedTime = fsObject.getLastAccessedTime();
        lastUpdatedTime = fsObject.getLastUpdatedTime();
        permissions = fsObject.getPermissions();
        ownerUserId = fsObject.getOwnerUserId();
        ownerGroupId = fsObject.getOwnerGroupId();

        Type type = null;
        int size = 0;
        if (fsObject instanceof File) {
            type = Type.FILE;
            size = ((File) fsObject).getSize();
        } else if (fsObject instanceof Directory) {
            type = Type.DIRECTORY;
            size = ((Directory) fsObject).getEntryCounty();
        }
        if (type == null) {
            throw new RuntimeException("not known file system object type " + fsObject.getClass());
        }
        this.type = type;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public Date getLastAccessedTime() {
        return lastAccessedTime;
    }

    public int getPermissions() {
        return permissions;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public int getOwnerGroupId() {
        return ownerGroupId;
    }

    public int getSize() {
        return size;
    }

    public enum Type {
        FILE, DIRECTORY
    }
}
