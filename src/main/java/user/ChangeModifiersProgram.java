package user;

import org.kohsuke.args4j.Argument;
import user.lib.Permissions;
import user.lib.ProgramName;
import user.lib.SimpleProgram;

import java.io.PrintStream;

@ProgramName("chmod")
public class ChangeModifiersProgram extends SimpleProgram {
    @Argument(required = true, index = 0)
    String modifiers;

    @Argument(required = true, index = 1)
    String path;

    @Override
    protected void runSimple(PrintStream systemOut) {
        Permissions permissions = new Permissions(modifiers);
        systemOperations.chmod(path, permissions.getValue());
    }
}
