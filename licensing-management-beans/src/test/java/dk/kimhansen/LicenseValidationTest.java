package dk.kimhansen;

import static dk.kimhansen.util.DateTimeUtils.getTodayOneMillisecondBeforeMidnight;
import static org.apache.commons.lang3.time.DateUtils.addDays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dk.kimhansen.util.DateTimeUtils;

public class LicenseValidationTest extends AbstractLicenceManagementTest {

    private DecryptLicenseKey decryptLicenseKey;
    private LicenseValidation licenseValidation;

    @Before
    public void setUp() {
        decryptLicenseKey = new DecryptLicenseKey(readKeysFromFile());
        licenseValidation = new LicenseValidation(decryptLicenseKey);
    }

    @Test
    public void testIsValid() {
        assertTrue(licenseValidation.isValid(createTestData(), CIPHER_TEXT));
    }

    @Test(expected = NullPointerException.class)
    public void testIsValidWithNullLicenseInformation() {
        assertTrue(licenseValidation.isValid(null, CIPHER_TEXT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidWithEmptyEncryptedLicenseKey() {
        assertTrue(licenseValidation.isValid(createTestData(), ""));
    }

    @Test(expected = NullPointerException.class)
    public void testIsValidWithNullEncryptedLicenseKey() {
        assertTrue(licenseValidation.isValid(createTestData(), null));
    }

    @Test
    public void testDaysBeforeExpiry() {
        Date todayStart = DateTimeUtils.getTodayAtMidnight();
        assertEquals(0, licenseValidation.daysBeforeExpiry(createLicenseWithExpiry(todayStart)));

        Date todayEnd = getTodayOneMillisecondBeforeMidnight();
        Date yesterdayEnd = addDays(todayEnd, -1);
        assertEquals(0, licenseValidation.daysBeforeExpiry(createLicenseWithExpiry(yesterdayEnd)));

        Date tommorrowStart = addDays(todayStart, 1);
        assertEquals(0, licenseValidation.daysBeforeExpiry(createLicenseWithExpiry(tommorrowStart)));

        Date yesterdayStart = addDays(todayStart, -1);
        assertEquals(-1, licenseValidation.daysBeforeExpiry(createLicenseWithExpiry(yesterdayStart)));
    }

    private LicenseInformation createLicenseWithExpiry(final Date expiryTimestamp) {
        LicenseInformation licenseInformation = new LicenseInformation();
        licenseInformation.setExpireTimestamp(expiryTimestamp);
        return licenseInformation;
    }

    @Test(expected = NullPointerException.class)
    public void testDaysBeforeExpiryWithNullLicenseInformation() {
        licenseValidation.daysBeforeExpiry(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDaysBeforeExpiryWithNullExpiryTimestamp() {
        licenseValidation.daysBeforeExpiry(createLicenseWithExpiry(null));
    }

}
