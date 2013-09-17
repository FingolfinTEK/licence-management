package dk.kimhansen;

public enum LicenseType {
	TRIAL,
	PRODUCTION,
	BACKUP;

	public static LicenseType fromName(String name) {
		for (LicenseType value : values()) {
			if (value.name().equalsIgnoreCase(name))
				return value;
		}
		return null;
	}
}
