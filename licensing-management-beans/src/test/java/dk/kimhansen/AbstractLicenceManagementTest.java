package dk.kimhansen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class AbstractLicenceManagementTest {

    public String getCipherText() {
        try {
            File file = new File(AbstractLicenceManagementTest.class.getClassLoader().getResource("cipher.txt").getFile());
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
        }

        return "";
    }

    public LicenseInformation createTestData() {
        LicenseInformation info = new LicenseInformation();
        info.setContactEmail("some@some.other");
        info.setContactName("name");
        info.setOrganizationName("oname");
        info.setUniqueId("id");
        info.setExpire(true);
        info.setExpireTimestamp(new Date(123456789L));
        return info;
    }

    public AsymmetricCipherKeyPair readKeysFromFile() {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());

        try (Input input = new Input(getResourceAsStream("keys.txt"))) {
            return kryo.readObject(input, AsymmetricCipherKeyPair.class);
        }
    }

    private InputStream getResourceAsStream(final String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    public void createKeysAndWriteThemToFile(final String[] args) {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        generator.init(new RSAKeyGenerationParameters(BigInteger.valueOf(31), new SecureRandom(), 187, 13));

        AsymmetricCipherKeyPair keys = generator.generateKeyPair();
        Kryo kryo = new Kryo();

        try (Output output = new Output(new FileOutputStream("keys.txt"))) {
            kryo.writeObject(output, keys);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}