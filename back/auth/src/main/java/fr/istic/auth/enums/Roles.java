package fr.istic.auth.enums;

public enum Roles {
    RH("rh"),
    CHEF_DE_PROJET("cdp"),
    ADMIN("admin");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    // Helper method to check if a role string is valid.
    public static boolean contains(String test) {
        for (Roles r : Roles.values()) {
            if (r.toString().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    // Optionally, to get the enum from string:
    public static Roles fromString(String text) {
        for (Roles r : Roles.values()) {
            if (r.toString().equalsIgnoreCase(text)) {
                return r;
            }
        }
        throw new IllegalArgumentException("This role does not exist: " + text);
    }
}

