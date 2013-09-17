package dk.kimhansen;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class LicenseServlet
 */
@WebServlet("/license")
public class LicenseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private LicenseLookup licenseLookup;

	@Inject
	private EncryptLicenseKey encryptLicenseKey;

	@Inject
	private LicenseValidation licenseValidation;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject object = JsonHelper.parseJSON(request);
		LicenseInformation information = JsonHelper.readLicenseInformationFromJson(object);

		String encryptedLicense;
		if (StringUtils.isNotBlank(information.getUniqueId())) {
			information = licenseLookup.lookup(information);
			encryptedLicense = JsonHelper.readEncryptedLicenceFromJson(object);

			if (!licenseValidation.isValid(information, encryptedLicense)) {
				throw new IllegalArgumentException("License validation failed");
			}
		} else {
			information.setUniqueId(UUID.randomUUID().toString());
			encryptedLicense = getEncryptedLicense(information);

		}

		JsonHelper.wrtiteToJson(information, encryptedLicense, response.getOutputStream());
	}

	public String getEncryptedLicense(LicenseInformation information) {
		try {
			return encryptLicenseKey.encrypt(information);
		} catch (DataLengthException | IllegalStateException | InvalidCipherTextException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
