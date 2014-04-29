package kernel.os;

import kernel.Stats;
import kernel.util.SystemConstants;
import kernel.SystemOperations;
import kernel.fs.*;

public class Pylynux implements SystemOperations {
    private Directory root;
    private Directory currentDirectory;
    private ChannelRegistry channelRegistry;
    private Channel stdInOutChannel;

    private int userId, groupId;

    public Pylynux(Channel stdInOutChannel) {
        this.stdInOutChannel = stdInOutChannel;

        root = Directory.newRoot();
        root.updateOwnerUser(userId);
        root.updateOwnerGroup(groupId);
        root.updatePermissions(SystemConstants.DEFAULT_DIRECTORY_PERMISSIONS);

        currentDirectory = root;

        channelRegistry = new ChannelRegistry(SystemConstants.MAX_OPEN_FILES);

        channelRegistry.bind(STDIN, stdInOutChannel);
        channelRegistry.bind(STDOUT, stdInOutChannel);
        channelRegistry.bind(STDERR, stdInOutChannel);
    }

    @Override
    public String[] list(String path) {
        PathResolver resolver = new PathResolver(currentDirectory, path);
        Directory dir = resolver.resolve(Directory.class);
        checkAccess(dir, FileSystemObject.Action.EXECUTE);
        return dir.list();
    }

    @Override
    public void mkdir(String path) {
        PathResolver resolver = new PathResolver(currentDirectory, path);
        resolver.lastComponentShouldNonExistent();

        Directory dir = resolver.resolve(Directory.class);
        checkAccess(dir, FileSystemObject.Action.EXECUTE);

        Directory newDir = dir.newDirectory(resolver.getLastComponent());

        newDir.updateOwnerUser(userId);
        newDir.updateOwnerGroup(groupId);
        newDir.updatePermissions(SystemConstants.DEFAULT_DIRECTORY_PERMISSIONS);
    }

    @Override
    public String cwd() {
        return currentDirectory.getAbsolutePath();
    }

    @Override
    public void chdir(String path) {
        PathResolver resolver = new PathResolver(currentDirectory, path);
        Directory dir = resolver.resolve(Directory.class);
        checkAccess(dir, FileSystemObject.Action.EXECUTE);
        currentDirectory = dir;
    }

    @Override
    public Stats stat(String path) {
        PathResolver resolver = new PathResolver(currentDirectory, path);
        FileSystemObject fileSystemObject = resolver.resolve(FileSystemObject.class);
        return new Stats(fileSystemObject);
    }

    @Override
    public void open(int fd, String path, boolean append, boolean reset, boolean create) {
        PathResolver resolver = new PathResolver(currentDirectory, path);
        resolver.skipLastComponent();

        Directory dir = resolver.resolve(Directory.class);

        File file;
        String name = resolver.getLastComponent();
        FileSystemObject fsObject = dir.get(name);

        if (fsObject == null) {
            if (create) {
                file = dir.newFile(name);
                file.updateOwnerUser(userId);
                file.updateOwnerGroup(groupId);
                file.updatePermissions(SystemConstants.DEFAULT_FILE_PERMISSIONS);
            } else {
                throw new RuntimeException("no file exists '" + name + "' in " + dir);
            }
        } else if (fsObject instanceof File) {
            file = (File) fsObject;
        } else {
            throw new RuntimeException("couldn't open directory '" + name + "' in " + dir);
        }

        boolean readAccess = dir.checkHasAccess(userId, groupId,
                FileSystemObject.Action.READ);
        boolean writeAccess = dir.checkHasAccess(userId, groupId,
                FileSystemObject.Action.WRITE);
        if (!(readAccess || writeAccess)) {
            throw new RuntimeException("no access");
        }

        if (reset) {
            file.updateContent(new byte[0], 0);
        }

        file.updateLastUpdated();

        FileChannel channel = new FileChannel(file, readAccess, writeAccess);
        if (append) {
            channel.seek(file.getSize());
        }
        channelRegistry.bind(fd, channel);
    }

    @Override
    public int read(int fd, byte[] buf) {
        Channel channel = channelRegistry.get(fd);
        return channel.read(buf);
    }

    @Override
    public int write(int fd, byte[] buf) {
        return channelRegistry.get(fd).write(buf);
    }

    @Override
    public void close(int fd) {
        channelRegistry.bind(fd, null);
    }

    @Override
    public void exec(Program program, String[] args) {
        program.setSystemOperations(this);
        program.setArguments(args);

        program.run();

        channelRegistry.bind(STDIN, stdInOutChannel);
        channelRegistry.bind(STDOUT, stdInOutChannel);
        channelRegistry.bind(STDERR, stdInOutChannel);
    }

    @Override
    public void chmod(String path, int value) {
        PathResolver pathResolver = new PathResolver(currentDirectory, path);
        FileSystemObject fsObject = pathResolver.resolve(FileSystemObject.class);
        checkAccess(fsObject, FileSystemObject.Action.WRITE);
        fsObject.updatePermissions(value);
    }

    private void checkAccess(FileSystemObject fsObject,
                             FileSystemObject.Action action) {
        if (!fsObject.checkHasAccess(userId, groupId, action)) {
            throw new RuntimeException("no access");
        }
    }
}
