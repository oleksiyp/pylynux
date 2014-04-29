package user;

import org.kohsuke.args4j.Argument;
import user.lib.ProgramName;
import user.lib.SimpleProgram;

import java.io.PrintStream;

@ProgramName("cd")
public class ChangeDirectoryProgram extends SimpleProgram {
    @Argument(required = true)
    String path;

    @Override
    protected void runSimple(PrintStream systemOut) {
        systemOperations.chdir(path);
    }
}
