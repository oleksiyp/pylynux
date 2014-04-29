package kernel.fs;

import java.util.Date;

public class FileSystemObject {
    protected final Directory parent;
    protected final String name;

    private Date creationTime;
    private Date lastAccessedTime;
    private Date lastUpdatedTime;

    private int permissions;

    private int ownerUserId;
    private int ownerGroupId;

    public FileSystemObject(Directory parent, String name) {
        this.parent = parent;
        this.name = name;

        creationTime = lastAccessedTime = lastUpdatedTime = new Date();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void updateLastAccessed() {
        this.lastAccessedTime = new Date();
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void updateLastUpdated() {
        this.lastUpdatedTime = new Date();
    }

    public int getPermissions() {
        return permissions;
    }

    public void updatePermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void updateOwnerUser(int uid) {
        this.ownerUserId = uid;
    }

    public int getOwnerGroupId() {
        return ownerGroupId;
    }

    public void updateOwnerGroup(int gid) {
        this.ownerGroupId = gid;
    }

    public boolean checkHasAccess(int userId, int groupId, Action action) {
        boolean isOwner = ownerUserId == userId;
        boolean isGroup = ownerGroupId == groupId;

        int mask = action.buildMask(isOwner, isGroup);
        return (permissions & mask) != 0;
    }

    public enum Action {
        READ, WRITE, EXECUTE;


        public int mask() {
            switch (this) {
                case READ: return 4;
                case WRITE: return 2;
                case EXECUTE: return 1;
            }
            return 0;
        }

        public int buildMask(boolean owner, boolean group) {
            int ownerMask = owner ? mask() << 6 : 0;
            int groupMask = group ? mask() << 3 : 0;
            int otherMask = mask();

            return ownerMask | groupMask | otherMask;
        }
    }
}
