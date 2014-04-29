package user;

import kernel.Stats;
import org.kohsuke.args4j.Argument;
import user.lib.Permissions;
import user.lib.ProgramName;
import user.lib.SimpleProgram;

import java.io.PrintStream;
import java.text.DateFormat;

@ProgramName("stat")
public class StatProgram extends SimpleProgram {
    @Argument(required=true)
    public String path;

    private static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

    @Override
    protected void runSimple(PrintStream systemOut) {
        Stats stats = systemOperations.stat(path);
        systemOut.printf("File: %s%n", path);

        String fileType = stats.getType() == Stats.Type.FILE ? "Regular File" : "Directory";
        String permissionString = new Permissions(stats.getPermissions())
                .asString(stats.getType() == Stats.Type.DIRECTORY);

        systemOut.printf("Size: %s %s%n", stats.getSize(), fileType);
        systemOut.printf("Access: %s Uid: (%d/%s) Gid: (%d/%s)%n", permissionString,
                stats.getOwnerUserId(),
                userGroupCatalog.getGroup(stats.getOwnerUserId()),
                stats.getOwnerGroupId(),
                userGroupCatalog.getUser(stats.getOwnerGroupId()));
        systemOut.printf("Access: %s%n", dateFormat.format(stats.getLastAccessedTime()));
        systemOut.printf("Create: %s%n", dateFormat.format(stats.getCreationTime()));
        systemOut.printf("Change: %s%n", dateFormat.format(stats.getLastUpdatedTime()));
    }
}
