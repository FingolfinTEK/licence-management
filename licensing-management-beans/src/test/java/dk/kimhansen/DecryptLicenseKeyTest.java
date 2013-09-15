package dk.kimhansen;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DecryptLicenseKeyTest extends AbstractLicenceManagementTest {

    private DecryptLicenseKey decryptLicenseKey;

    @Before
    public void setUp() {
        decryptLicenseKey = new DecryptLicenseKey(readKeysFromFile());
    }

    @Test
    public void testDecrypt() throws Exception {
        LicenseInformation decrypted = decryptLicenseKey.decrypt(CIPHER_TEXT);
        assertEquals(createTestData(), decrypted);
    }

}
