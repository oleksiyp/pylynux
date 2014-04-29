import extension.ConsoleChannel;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import kernel.os.Pylynux;
import user.KnownPrograms;
import user.shell.Shell;

public class App {
    public static void main(String[] args) {
        ConsoleReader console;
        try {
            console = new ConsoleReader();
            console.setHistoryEnabled(true);

            Pylynux nix = new Pylynux(new ConsoleChannel(console));

            Shell shell = new Shell(nix, new KnownPrograms()
                    .getProgramFactory());
            shell.commandLoop(console);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
