package dk.kimhansen;

import java.io.InputStream;

import javax.enterprise.inject.Produces;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

public class RSAKeyProducer {
    @Produces
    public AsymmetricCipherKeyPair produceRSAKeyPair() {
        // TODO this should be properly implemented for the application to work properly
        return readKeysFromFile();
    }

    private AsymmetricCipherKeyPair readKeysFromFile() {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());

        try (Input input = new Input(getResourceAsStream("META-INF/keys.txt"))) {
            return kryo.readObject(input, AsymmetricCipherKeyPair.class);
        }
    }

    private InputStream getResourceAsStream(final String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
