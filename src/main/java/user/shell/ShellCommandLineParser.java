package user.shell;

import kernel.SystemOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShellCommandLineParser {
    private final Pattern REDIRECT_PATTERN = Pattern.compile("(>>?|<) *([^ ]+|&)");
    private static final String ARGUMENTS_SPLITTER = " ";
    private String commandName;
    private String[] arguments;

    private final List<Redirect> redirectList = new ArrayList<>();

    public void parse(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }

        line = extractRedirects(line);

        String[] splitCmd = line.split(ARGUMENTS_SPLITTER);
        commandName = splitCmd[0];
        arguments = Arrays.copyOfRange(splitCmd, 1, splitCmd.length);
    }

    private String extractRedirects(String line) {
        StringBuffer buf = new StringBuffer();
        Matcher matcher = REDIRECT_PATTERN.matcher(line);
        while (matcher.find()) {
            String type = matcher.group(1);
            String filename = matcher.group(2);

            Redirect redir = new Redirect();
            redir.type = type;
            redir.filename = filename;

            redirectList.add(redir);

            matcher.appendReplacement(buf, "");
        }
        matcher.appendTail(buf);
        return buf.toString();
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void useRedirects(SystemOperations sysOps) {
        for (Redirect redirect : redirectList) {
            redirect.useOn(sysOps);
        }
    }

    private static class Redirect {
        public static final String IN_TYPE = "<";
        public static final String OUT_TYPE = ">";
        public static final String OUT_APPEND_TYPE = ">>";
        public String type;
        public String filename;

        public void useOn(SystemOperations sysOps) {
            switch (type) {
                case IN_TYPE:
                    sysOps.open(SystemOperations.STDIN, filename, false, false, false);
                    break;
                case OUT_TYPE:
                    sysOps.open(SystemOperations.STDOUT, filename, false, true, true);
                    break;
                case OUT_APPEND_TYPE:
                    sysOps.open(SystemOperations.STDOUT, filename, true, false, true);
                    break;
            }
        }
    }
}
