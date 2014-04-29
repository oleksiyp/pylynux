package user.shell;

import jline.console.ConsoleReader;
import kernel.SystemOperations;
import kernel.os.Program;
import user.lib.ProgramFactory;

public class Shell {

    private final SystemOperations sysCalls;
    private final ProgramFactory factory;

    public Shell(SystemOperations sysCalls, ProgramFactory factory) {
        this.sysCalls = sysCalls;
        this.factory = factory;
    }


    public void commandLoop(ConsoleReader console) throws Exception {
        String line;
        updatePrompt(console);
        while ((line = console.readLine()) != null) {
            try {
                command(line);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace(System.out);
            }
            updatePrompt(console);
        }
    }

    private void updatePrompt(ConsoleReader console) {
        console.setPrompt(sysCalls.cwd() + "$ ");
    }

    public void command(String line) {
        ShellCommandLineParser parser = new ShellCommandLineParser();
        parser.parse(line);
        parser.useRedirects(sysCalls);

        Program program = factory.newProgram(parser.getCommandName());
        sysCalls.exec(program, parser.getArguments());
    }
}
