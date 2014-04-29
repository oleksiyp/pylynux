package user;

import kernel.SystemOperations;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertArrayEquals;

public class ListingProgramTest {

    private SystemOperations sysMock;

    @Before
    public void setUp() throws Exception {
        sysMock = EasyMock.createMock(SystemOperations.class);
    }

    @Test
    public void testListWithoutOptions() throws Exception {
        EasyMock.reset(sysMock);

        String expectedOutput = "a\nb\n";

        EasyMock.expect(sysMock.cwd()).andReturn("/");
        EasyMock.expect(sysMock.list("/")).andReturn(new String[]{"a", "b"});

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        TestTools.captureExpectedOutput(sysMock, bout);

        EasyMock.replay(sysMock);

        ListingProgram listing = new ListingProgram();
        listing.setSystemOperations(sysMock);
        listing.setArguments(new String[0]);
        listing.run();

        EasyMock.verify(sysMock);

        assertArrayEquals(expectedOutput.getBytes(), bout.toByteArray());
    }
}