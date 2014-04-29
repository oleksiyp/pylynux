package user.lib;

import kernel.os.Program;

import java.util.HashMap;
import java.util.Map;

public class ProgramFactory {
    private final Map<String, Class<? extends Program>> registry;

    public ProgramFactory() {
        this.registry = new HashMap<>();
    }

    public void register(Class<? extends Program> clazz) {
        ProgramName name = clazz.getAnnotation(ProgramName.class);
        if (name == null) {
            throw new RuntimeException("no annotation " + ProgramName.class.getName() +
                    " found for " + clazz);
        }

        if (registry.containsKey(name.value())) {
            throw new RuntimeException("program with name '" +
                    name.value() + "' already registered");
        }

        registry.put(name.value(), clazz);
    }

    public Program newProgram(String name) {
        Class<? extends Program> programClass = registry.get(name);
        if (programClass == null) {
            throw new RuntimeException(name + ": command not found");
        }
        try {
            return programClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("failed to create program instance", e);
        }
    }
}
