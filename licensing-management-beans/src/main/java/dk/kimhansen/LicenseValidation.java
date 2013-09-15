package dk.kimhansen;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class LicenseValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    private final DecryptLicenseKey licenseKeyDecrypter;

    @Inject
    public LicenseValidation(final DecryptLicenseKey licenseKeyDecrypter) {
        this.licenseKeyDecrypter = licenseKeyDecrypter;
    }

    /**
     * Checks whether the provided license information matches the information stored in the encrypted license key
     * 
     * @param licenseInformation
     * @param encryptedLicenseKey
     * @return
     */
    public boolean isValid(final LicenseInformation licenseInformation, final String encryptedLicenseKey) {
        Validate.notNull(licenseInformation, "Licence information must not be null");
        Validate.notEmpty(encryptedLicenseKey, "Encrypted licence key must not be empty");
        return licenseInformation.equals(getDecrypted(encryptedLicenseKey));
    }

    private LicenseInformation getDecrypted(final String encryptedLicenseKey) {
        LicenseInformation retVal = null;

        try {
            retVal = licenseKeyDecrypter.decrypt(encryptedLicenseKey);
        } catch (DataLengthException | IllegalStateException | InvalidCipherTextException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public int daysBeforeExpiry(final LicenseInformation licenseInformation) {
        Validate.notNull(licenseInformation, "Licence information must not be null");
        return licenseInformation.isExpire() ? calculateDaysBeforeExpiry(licenseInformation) : Integer.MAX_VALUE;
    }

    private int calculateDaysBeforeExpiry(final LicenseInformation licenseInformation) {
        Validate.notNull(licenseInformation.getExpireTimestamp(), "Expiry timestamp must not be null");
        Date expiryDate = licenseInformation.getExpireTimestamp();
        return getDifferenceInDays(expiryDate, new Date()).intValue();
    }

    private Long getDifferenceInDays(final Date expiryDate, final Date currentDate) {
        return getDateDifference(expiryDate, currentDate, TimeUnit.DAYS);
    }

    private Long getDateDifference(final Date toDate, final Date fromDate, final TimeUnit timeUnit) {
        long differenceInMillies = toDate.getTime() - fromDate.getTime();
        return timeUnit.convert(differenceInMillies, TimeUnit.MILLISECONDS);
    }

}
