package dk.kimhansen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DecryptLicenseKeyTest extends AbstractLicenceManagementTest {
    DecryptLicenseKey decryptLicenseKey;

    @Before
    public void setUp() {
        decryptLicenseKey = new DecryptLicenseKey();
        decryptLicenseKey.setRsaKeys(readKeysFromFile());
    }

    @Test
    public void testDecrypt() throws Exception {
        LicenseInformation decrypted = decryptLicenseKey.decrypt(getCipherText());
        Assert.assertEquals(createTestData(), decrypted);
    }

}
