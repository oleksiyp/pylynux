package user;

import kernel.SystemOperations;
import org.kohsuke.args4j.Argument;
import user.lib.ProgramName;
import user.lib.SimpleProgram;

import java.io.PrintStream;
import java.util.Arrays;

@ProgramName("cat")
public class CatProgram extends SimpleProgram {
    public static final int FD = 4;
    @Argument(required = true)
    public String path;

    @Override
    protected void runSimple(PrintStream systemOut) {
        SystemOperations sysOps = systemOperations;
        sysOps.open(FD, path, false, false, false);
        try {
            byte[] buf = new byte[4096];
            int r;
            while ((r = sysOps.read(FD, buf)) != 0) {
                buf = Arrays.copyOf(buf, r);
                sysOps.write(SystemOperations.STDOUT, buf);
            }
        } finally {
            sysOps.close(FD);
        }
    }
}
