package user;

import org.kohsuke.args4j.Argument;
import user.lib.ProgramName;
import user.lib.SimpleProgram;

import java.io.PrintStream;

@ProgramName("touch")
public class TouchProgram extends SimpleProgram {
    @Argument(required=true)
    public String path;

    @Override
    protected void runSimple(PrintStream sysOut) {
        systemOperations.open(4, path, false, false, true);
        systemOperations.close(4);
    }
}
