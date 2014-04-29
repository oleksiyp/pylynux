package user;

import kernel.SystemOperations;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;

import java.io.ByteArrayOutputStream;

public class TestTools {
    public static void captureExpectedOutput(SystemOperations sysMock, final ByteArrayOutputStream bout) {
        final Capture<byte[]> capture = new Capture<>();

        EasyMock.expect(sysMock.write(EasyMock.eq(SystemOperations.STDOUT),
                EasyMock.capture(capture))).andAnswer(new IAnswer<Integer>() {
            @Override
            public Integer answer() throws Throwable {
                byte[] buf = capture.getValue();
                bout.write(buf);
                return buf.length;
            }
        }).anyTimes();
    }
}
