package kernel.fs;

import kernel.util.SystemConstants;

import java.util.HashMap;
import java.util.Map;

public class Directory extends FileSystemObject {
    private final Directory root;
    private final Map<String, FileSystemObject> directoryMap;

    private Directory(Directory root, Directory parent, String name) {
        super(parent, name);
        this.root = root;
        directoryMap = new HashMap<>();
    }

    public String getAbsolutePath() {
        if (isRoot()) {
            return SystemConstants.PATH_SEPARATOR;
        }
        if (parent.isRoot()) {
            return SystemConstants.PATH_SEPARATOR + name;
        }
        return parent.getAbsolutePath() + SystemConstants.PATH_SEPARATOR + name;
    }

    public boolean isRoot() {
        return root == null;
    }

    public Directory newDirectory(String name) {
        Directory dir = new Directory(getRootDirectory(), this, name);
        directoryMap.put(name, dir);
        return dir;
    }

    public File newFile(String name) {
        File file = new File(this, name);
        directoryMap.put(name, file);

        return file;
    }

    public FileSystemObject get(String name) {
        return directoryMap.get(name);
    }

    public String []list() {
        return directoryMap.keySet().toArray(
                new String[directoryMap.size()]);
    }

    public Directory getRootDirectory() {
        if (isRoot()) {
            return this;
        }
        return root;
    }

    public Directory getParentDirectory() {
        if (isRoot()) {
            return this;
        }
        return parent;
    }


    public static Directory newRoot() {
        return new Directory(null, null, "/");
    }

    public int getEntryCounty() {
        return directoryMap.size();
    }

    @Override
    public String toString() {
        return getAbsolutePath();
    }
}
