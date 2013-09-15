package dk.kimhansen;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.util.encoders.Base64;

public class DecryptLicenseKey extends AbstractEncryptDecryptLincenseKey {

    private static final long serialVersionUID = 1L;

    public LicenseInformation decrypt(final String crypted) throws DataLengthException, IllegalStateException, InvalidCipherTextException,
            UnsupportedEncodingException {
        byte[] decoded = Base64.decode(crypted);
        byte[] cryptedDigest = Arrays.copyOf(decoded, 96);
        byte[] digest = decryptDigestWithRSA(cryptedDigest);
        byte[] cipherText = Arrays.copyOfRange(decoded, 96, decoded.length);

        PaddedBufferedBlockCipher decryptCipher = createDecryptCipherForDigest(digest);
        byte[] processed = processDataWithBufferedBlockCipher(cipherText, decryptCipher);
        return (LicenseInformation) SerializationUtils.deserialize(processed);
    }

    private byte[] decryptDigestWithRSA(final byte[] digest) {
        RSAEngine rsaEngine = new RSAEngine();
        rsaEngine.init(false, getRSAPrivateKey());
        byte[] data = Arrays.copyOfRange(digest, 0, 48);
        byte[] data2 = Arrays.copyOfRange(digest, 48, digest.length);
        return ArrayUtils.addAll(procesDataUsingRSA(rsaEngine, data), procesDataUsingRSA(rsaEngine, data2));
    }

}
