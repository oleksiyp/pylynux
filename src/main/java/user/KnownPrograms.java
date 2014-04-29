package user;

import user.lib.ProgramFactory;

public class KnownPrograms {
    private final ProgramFactory programFactory = new ProgramFactory();
    {
        programFactory.register(ListingProgram.class);
        programFactory.register(MakeDirectoryProgram.class);
        programFactory.register(ChangeDirectoryProgram.class);
        programFactory.register(CatProgram.class);
        programFactory.register(EchoProgram.class);
        programFactory.register(ChangeModifiersProgram.class);
        programFactory.register(StatProgram.class);
        programFactory.register(TouchProgram.class);
    }

    public ProgramFactory getProgramFactory() {
        return programFactory;
    }
}
