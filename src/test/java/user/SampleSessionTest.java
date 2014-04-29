package user;

import kernel.os.Pylynux;
import org.junit.Before;
import org.junit.Test;
import user.shell.Shell;

public class SampleSessionTest {

    private Shell shell;
    private TestChannel testChannel;

    @Before
    public void setUp() throws Exception {
        testChannel = new TestChannel();
        Pylynux nix = new Pylynux(testChannel);

        shell = new Shell(nix,
                new KnownPrograms().getProgramFactory());
    }

    @Test
    public void testSimpleSession() throws Exception {
        cmd("ls");
        cmd("mkdir myfirstdir");
        cmd("ls -a");
        cmd("ls -l");
        cmd("cd myfirstdir");
        cmd("ls");
        cmd("ls -a");
        cmd("touch afile");
        cmd("ls -l");
        cmd("cat afile");
        cmd("echo some content >> afile");
        cmd("cat afile");
        cmd("ls -l");
        cmd("chmod 755 afile");
        cmd("ls -l");
        cmd("stat afile");

        System.out.println(new String(testChannel.getOutput()));
    }

    private void cmd(String cmd) {
        testChannel.write(new byte[] {'$', ' '});
        testChannel.write(cmd.getBytes());
        testChannel.write(new byte[] {'\n'});
        shell.command(cmd);
    }
}
