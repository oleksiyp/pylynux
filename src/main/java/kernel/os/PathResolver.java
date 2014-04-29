package kernel.os;

import kernel.util.SystemConstants;
import kernel.fs.Directory;
import kernel.fs.FileSystemObject;

public class PathResolver {
    private final String[] pathComponents;
    private final Directory startDir;
    private boolean skipLast;
    private boolean lastComponentShouldNotExists;
    private String path;

    public PathResolver(Directory startDir, String path) {
        this.path = path;
        if (path.isEmpty()) {
            throw new RuntimeException("path could not be empty");
        }
        if (path.startsWith(SystemConstants.PATH_SEPARATOR)) {
            startDir = startDir.getRootDirectory();
            path = path.substring(1);
        }
        this.startDir = startDir;
        pathComponents = path.split(SystemConstants.PATH_SEPARATOR);
    }

    public <T extends FileSystemObject> T resolve(Class<T> desiredType) {

        FileSystemObject obj = startDir;
        for (int i = 0; i < pathComponents.length; i++) {
            String pathComponent = pathComponents[i];
            boolean isLast = i == pathComponents.length - 1;
            if (isLast && skipLast) {
                break;
            }

            if (pathComponent.isEmpty()) {
                continue;
            }

            if (!(obj instanceof Directory)) {
                throw new RuntimeException("could not resolve " + this);
            }

            Directory dirObj = (Directory) obj;
            if (SystemConstants.SAME_DIR.equals(pathComponent)) {
                continue;
            }
            if (SystemConstants.UPPER_DIR.equals(pathComponent)) {
                obj = dirObj.getParentDirectory();
                continue;
            }

            obj = dirObj.get(pathComponent);

            if (obj == null) {
                throw new RuntimeException("could not resolve " + this);
            }
        }

        if (!desiredType.isAssignableFrom(obj.getClass())) {
            throw new RuntimeException("could not resolve " + this);
        }

        if (lastComponentShouldNotExists) {
            if (obj instanceof Directory) {
                Directory dir = (Directory) obj;
                String lastComponent = getLastComponent();
                FileSystemObject fsObj = dir.get(lastComponent);
                if (fsObj != null) {
                    throw new RuntimeException("already exists " + this);
                }
            }
        }

        return desiredType.cast(obj);
    }

    public String getLastComponent() {
        return pathComponents[pathComponents.length - 1];
    }

    @Override
    public String toString() {
        return path;
    }

    public void skipLastComponent() {
        skipLast = true;
    }

    public void lastComponentShouldNonExistent() {
        skipLast = true;
        lastComponentShouldNotExists = true;
    }
}
