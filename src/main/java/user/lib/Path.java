package user.lib;

import kernel.util.SystemConstants;

public class Path {
    private final String value;

    public Path(String val) {
        value = val;
    }

    public Path(Path path, String component) {
        if (path.isRoot()) {
            value = SystemConstants.PATH_SEPARATOR + component;
        } else {
            value = path.value + SystemConstants.PATH_SEPARATOR + component;
        }
    }

    public boolean isRoot() {
        return SystemConstants.PATH_SEPARATOR.equals(value);
    }

    public String getValue() {
        return value;
    }
}
