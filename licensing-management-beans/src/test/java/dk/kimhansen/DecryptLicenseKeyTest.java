package dk.kimhansen;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

public class DecryptLicenseKeyTest extends AbstractLicenceManagementTest {

    private DecryptLicenseKey decryptLicenseKey;

    @Before
    public void setUp() {
        decryptLicenseKey = new DecryptLicenseKey(new RSAKeyProducer());
    }

    @Test
    public void testDecrypt() throws Exception {
        LicenseInformation decrypted = decryptLicenseKey.decrypt(CIPHER_TEXT);
        Assertions.assertThat(decrypted).isEqualTo(createTestData());
    }

}
