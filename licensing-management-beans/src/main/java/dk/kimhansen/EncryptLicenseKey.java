package dk.kimhansen;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.inject.Named;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.util.encoders.Base64;

@Named
public class EncryptLicenseKey extends AbstractEncryptDecryptLincenseKey {

    private static final long serialVersionUID = 1L;

    public EncryptLicenseKey(final AsymmetricCipherKeyPair rsaKeys) {
        super(rsaKeys);
    }

    public String encrypt(final LicenseInformation information) throws DataLengthException, IllegalStateException,
            InvalidCipherTextException, UnsupportedEncodingException {
        byte[] data = SerializationUtils.serialize(information);
        byte[] digest = createDigest(data);

        PaddedBufferedBlockCipher encryptCipher = createEncryptCipherForDigest(digest);
        byte[] processed = processDataWithBufferedBlockCipher(data, encryptCipher);
        byte[] output = ArrayUtils.addAll(encryptDigestWithRSA(digest), processed);
        return new String(Base64.encode(output));
    }

    private byte[] createDigest(final byte[] data) {
        Digest messageDigest = new SHA512Digest();
        byte[] retValue = new byte[messageDigest.getDigestSize()];
        messageDigest.update(data, 0, data.length);
        messageDigest.doFinal(retValue, 0);
        return retValue;
    }

    private byte[] encryptDigestWithRSA(final byte[] digest) {
        RSAEngine rsaEngine = new RSAEngine();
        rsaEngine.init(true, getRSAPublicKey());
        byte[] data = Arrays.copyOfRange(digest, 0, 32);
        byte[] data2 = Arrays.copyOfRange(digest, 32, digest.length);
        return ArrayUtils.addAll(procesDataUsingRSA(rsaEngine, data), procesDataUsingRSA(rsaEngine, data2));
    }
}
