package kernel.os;

import kernel.SystemOperations;

public abstract class Program {
    protected SystemOperations systemOperations;
    protected String [] arguments;

    public void setSystemOperations(SystemOperations systemOperations) {
        this.systemOperations = systemOperations;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public abstract void run();
}
