package user;

import org.kohsuke.args4j.Argument;
import user.lib.ProgramName;
import user.lib.SimpleProgram;

import java.io.PrintStream;
import java.util.List;

@ProgramName("echo")
public class EchoProgram extends SimpleProgram {

    @Argument(multiValued = true)
    public List<String> allArgs;

    @Override
    protected void runSimple(PrintStream systemOut) {
        boolean firstFlag = true;
        for (String str : allArgs) {
            if (!firstFlag) {
                systemOut.print(' ');
            }
            firstFlag = false;
            systemOut.print(str);
        }
        systemOut.println();
    }
}
