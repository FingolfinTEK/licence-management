package dk.kimhansen;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class LicenseInformationTest extends AbstractLicenceManagementTest {

    @Test
    public void testFromString() {
        assertThat(createTestData().toString()).isEqualTo("id§oname§TRIAL§true§123456789§name§some@some.other");
        assertThat(createTestData(null).toString()).isEqualTo("id§oname§TRIAL§true§null§name§some@some.other");
    }

}
