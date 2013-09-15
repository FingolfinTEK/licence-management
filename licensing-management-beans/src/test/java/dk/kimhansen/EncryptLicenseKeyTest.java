package dk.kimhansen;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EncryptLicenseKeyTest extends AbstractLicenceManagementTest {

    private EncryptLicenseKey encrypt;

    @Before
    public void setUp() {
        encrypt = new EncryptLicenseKey(readKeysFromFile());
    }

    @Test
    public void testEncrypt() throws Exception {
        LicenseInformation info = createTestData();

        String cipherText = encrypt.encrypt(info);
        System.out.println(cipherText);
        assertEquals(cipherText, CIPHER_TEXT);
    }

}
