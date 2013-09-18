package dk.kimhansen;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import dk.kimhansen.util.DateTimeUtils;

public class JsonHelper {

	public static final String ID = "ID";
	public static final String ORGANIZATION_NAME = "OrganizationName";
	public static final String LICENSE_TYPE = "LicenseType";
	public static final String EXPIRE = "Expire";
	public static final String EXPIRE_TIMESTAMP = "ExpireTimestamp";
	public static final String CONTACT_NAME = "ContactName";
	public static final String CONTACT_EMAIL = "ContactEmail";
	public static final String ENCRYPTED_LICENSE = "EncryptedLicense";

	public static void writeLicenseInformationToJson(final LicenseInformation found, final JsonWriter writer) throws IOException {
		writer.name(ID).value(found.getUniqueId());
		writer.name(ORGANIZATION_NAME).value(found.getOrganizationName());
		writer.name(EXPIRE).value(found.isExpire());
		writer.name(CONTACT_NAME).value(found.getContactName());
		writer.name(CONTACT_EMAIL).value(found.getContactEmail());
		writer.name(LICENSE_TYPE).value(found.getLicenseType().name());

		if (found.getExpireTimestamp() != null) {
			writer.name(EXPIRE_TIMESTAMP).value(DateTimeUtils.toIsoTimestamp(found.getExpireTimestamp()));
		}
	}

	public static JsonObject parseJSON(final HttpServletRequest request) throws IOException {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(request.getReader());
		JsonObject object = element.getAsJsonObject();
		return object;
	}

	public static LicenseInformation readLicenseInformationFromJson(final JsonObject object) {
		String id = readFieldIfPresent(object, ID);
		String organizationName = object.get(ORGANIZATION_NAME).getAsString();
		String licenseType = readFieldIfPresent(object, LICENSE_TYPE);
		Boolean expire = object.has(EXPIRE) ? object.get(EXPIRE).getAsBoolean() : false;
		String expireTimestamp = readFieldIfPresent(object, EXPIRE_TIMESTAMP);
		String contactName = object.get(CONTACT_NAME).getAsString();
		String contactEmail = object.get(CONTACT_EMAIL).getAsString();

		return new LicenseInformation(id, organizationName, LicenseType.fromName(licenseType, LicenseType.TRIAL), expire,
				DateTimeUtils.parseIsoTimestamp(expireTimestamp), contactName, contactEmail);
	}

	public static String readFieldIfPresent(final JsonObject object, String fieldName) {
		return object.has(fieldName) ? object.get(fieldName).getAsString() : "";
	}

	public static String readEncryptedLicenceFromJson(final JsonObject object) {
		return readFieldIfPresent(object, ENCRYPTED_LICENSE);
	}

	public static void wrtiteToJson(final LicenseInformation found, final String encryptedLicense, final OutputStream outputStream) throws IOException {
		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))) {
			writer.beginObject();
			writeLicenseInformationToJson(found, writer);
			writer.name(ENCRYPTED_LICENSE).value(encryptedLicense);
			writer.endObject();
		}
	}

}
