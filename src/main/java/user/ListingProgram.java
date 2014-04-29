package user;

import kernel.Stats;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import user.lib.Path;
import user.lib.Permissions;
import user.lib.ProgramName;
import user.lib.SimpleProgram;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ProgramName("ls")
public class ListingProgram extends SimpleProgram {
    @Option(name = "-l", usage = "shows long listing of files")
    public boolean longListing;

    @Option(name = "-a", usage = "shows hidden files")
    public boolean allFiles;

    @Argument
    public String path;

    private static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

    @Override
    protected void runSimple(PrintStream systemOut) {
        String path = this.path != null ? this.path : systemOperations.cwd();
        List<String> names = new ArrayList<>();
        names.add(".");
        names.add("..");
        names.addAll(Arrays.asList(systemOperations.list(path)));

        for (String name : names) {
            if (name.startsWith(".") && !allFiles) {
                continue;
            }
            if (longListing) {
                Stats stat = systemOperations.stat(
                        new Path(new Path(path), name).getValue()
                );

                Permissions permissions = new Permissions(stat.getPermissions());
                String permString = permissions.asString(stat.getType() == Stats.Type.DIRECTORY);

                systemOut.printf("%s %s %s %s %s%n",
                        permString,
                        userGroupCatalog.getUser(stat.getOwnerUserId()),
                        userGroupCatalog.getGroup(stat.getOwnerGroupId()),
                        dateFormat.format(stat.getLastUpdatedTime()),
                        name
                );
            } else {
                systemOut.println(name);
            }
        }
    }
}
