package user.lib;

import kernel.os.Program;
import kernel.SystemOperations;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.PrintStream;

public abstract class SimpleProgram extends Program {
    @Option(name = "-h", usage = "shows usage message")
    protected boolean help;

    protected UserGroupCatalog userGroupCatalog;

    @Override
    public final void run() {
        userGroupCatalog = UserGroupCatalog.VERY_BASIC;

        PrintStream systemOut = new WrapperPrintStream(
                systemOperations,
                SystemOperations.STDOUT);

        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(arguments);
        } catch (CmdLineException e) {
            throw new RuntimeException(e.getMessage());
        }

        if (help) {
            parser.printUsage(systemOut);
            return;
        }

        runSimple(systemOut);
    }

    protected abstract void runSimple(PrintStream sysOut);
}
