package dk.kimhansen;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class LicenseValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    private @Inject DecryptLicenseKey licenseKeyDecrypter;

    public boolean isValid(final LicenseInformation licenceInformation, final String encryptedLicenceKey) {
        Validate.notNull(licenceInformation);
        Validate.notEmpty(encryptedLicenceKey);
        return licenceInformation.equals(getDecrypted(encryptedLicenceKey));
    }

    private LicenseInformation getDecrypted(final String encryptedLicenceKey) {
        LicenseInformation retVal = null;

        try {
            retVal = licenseKeyDecrypter.decrypt(encryptedLicenceKey);
        } catch (DataLengthException | IllegalStateException | InvalidCipherTextException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return retVal;
    }

}
