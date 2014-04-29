package user;

import kernel.os.Program;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.lib.ProgramFactory;
import user.lib.ProgramName;

public class ProgramFactoryTest {
    private ProgramFactory programFactory;

    @Before
    public void setUp() throws Exception {
        programFactory = new ProgramFactory();
    }

    @Test
    public void testOneProgram() throws Exception {
        programFactory.register(FakeTestProgram.class);
        Program res = programFactory.newProgram("a");
        Assert.assertNotNull(res);
        Assert.assertEquals(FakeTestProgram.class, res.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void testSecondProgram() throws Exception {
        programFactory.register(FakeTestProgram.class);
        programFactory.newProgram("b");
    }

    @Test(expected = RuntimeException.class)
    public void testRegisterWithSameName() throws Exception {
        programFactory.register(FakeTestProgram.class);
        programFactory.register(FakeTestProgram.class);
    }

    @ProgramName("a")
    public static class FakeTestProgram extends Program {
        @Override
        public void run() {

        }
    }
}