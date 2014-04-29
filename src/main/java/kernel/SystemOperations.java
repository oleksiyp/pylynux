package kernel;

import kernel.os.Program;

public interface SystemOperations {
    int STDIN = 0;
    int STDOUT = 1;
    int STDERR = 2;

    String []list(String path);

    void mkdir(String path);

    String cwd();

    void chdir(String path);

    Stats stat(String path);

    void open(int fd, String path, boolean append, boolean reset, boolean create);

    int read(int fd, byte []buf);

    int write(int fd, byte []buf);

    void close(int fd);

    void exec(Program program, String []args);

    void chmod(String path, int value);
}
