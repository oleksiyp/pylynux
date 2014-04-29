package user.lib;

public class Permissions {
    private final int value;

    public static final int MASK_BIT_OFFSET = 3;
    public static final int MASK_BITS = 07;

    public Permissions(int value) {
        this.value = value;
    }

    public Permissions(String modifiers) {
        this.value = Integer.parseInt(modifiers, 8);
    }

    public String asString(boolean directory) {
        StringBuilder builder = new StringBuilder(9);
        builder.append(directory ? 'd' : '-');
        for (Who who : Who.values()) {
            int val = who.getBits(value);
            for (What what : What.values()) {
                if (what.hasFlag(val)) {
                    builder.append(what.asString());
                } else {
                    builder.append("-");
                }
            }
        }

        return builder.toString();
    }

    public int getValue() {
        return value;
    }

    enum What {
        READ, WRITE, EXECUTE;

        public char asString() {
            switch (this) {
                case READ: return 'r';
                case WRITE: return 'w';
                case EXECUTE: return 'x';
            }
            return ' ';
        }

        private int mask() {
            return 1 << (values().length - ordinal() - 1);
        }

        public boolean hasFlag(int v) {
            return (mask() & v) != 0;
        }
    }
    enum Who {
        USER, GROUP, OTHER;

        public int getBits(int val) {
            int order = values().length - ordinal() - 1;
            int v = (val >> (order * MASK_BIT_OFFSET));
            return v & MASK_BITS;
        }
    }
}
