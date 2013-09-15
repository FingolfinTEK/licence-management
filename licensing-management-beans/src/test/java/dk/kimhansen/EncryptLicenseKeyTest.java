package dk.kimhansen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EncryptLicenseKeyTest extends AbstractLicenceManagementTest {

    private EncryptLicenseKey encrypt;

    @Before
    public void setUp() {
        encrypt = new EncryptLicenseKey();
        encrypt.setRsaKeys(readKeysFromFile());
    }

    @Test
    public void testEncrypt() throws Exception {
        LicenseInformation info = createTestData();

        String cipherText = encrypt.encrypt(info);
        System.out.println(cipherText);
        Assert.assertEquals(cipherText, getCipherText());
    }

}
