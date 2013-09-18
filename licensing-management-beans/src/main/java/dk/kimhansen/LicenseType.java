package dk.kimhansen;

public enum LicenseType {
    TRIAL, PRODUCTION, BACKUP;

    public static LicenseType fromName(final String name) {
        return fromName(name, null);
    }

    public static LicenseType fromName(final String name, final LicenseType defaultValue) {
        for (LicenseType value : values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return defaultValue;
    }
}
