package user.lib;

public class UserGroupCatalog {
    private final String []users;
    private final String []groups;

    public static UserGroupCatalog VERY_BASIC = new UserGroupCatalog(new String[]{"default"},
            new String[]{"default"});

    public UserGroupCatalog(String[] users, String[] groups) {
        this.users = users;
        this.groups = groups;
    }

    public String getUser(int uid) {
        if (uid < 0 || uid >= users.length) {
            return "unknown";
        }
        return users[uid];
    }

    public String getGroup(int gid) {
        if (gid < 0 || gid >= groups.length) {
            return "unknown";
        }
        return groups[gid];
    }
}
