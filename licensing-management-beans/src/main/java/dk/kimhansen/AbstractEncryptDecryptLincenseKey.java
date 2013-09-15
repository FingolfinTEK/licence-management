package dk.kimhansen;

import java.io.Serializable;
import java.util.Arrays;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class AbstractEncryptDecryptLincenseKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private final AsymmetricCipherKeyPair rsaKeys;

    @Inject
    public AbstractEncryptDecryptLincenseKey(final AsymmetricCipherKeyPair rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    public CipherParameters getRSAPublicKey() {
        return rsaKeys.getPublic();
    }

    public CipherParameters getRSAPrivateKey() {
        return rsaKeys.getPrivate();
    }

    protected byte[] processDataWithBufferedBlockCipher(final byte[] data, final BufferedBlockCipher cipher)
            throws InvalidCipherTextException {
        byte[] buffer = new byte[cipher.getOutputSize(data.length)];
        int length = cipher.processBytes(data, 0, data.length, buffer, 0);
        length += cipher.doFinal(buffer, length);

        return Arrays.copyOf(buffer, length);
    }

    protected PaddedBufferedBlockCipher createEncryptCipherForDigest(final byte[] digest) {
        return createCipherForDigest(digest, true);
    }

    protected PaddedBufferedBlockCipher createDecryptCipherForDigest(final byte[] digest) {
        return createCipherForDigest(digest, false);
    }

    private PaddedBufferedBlockCipher createCipherForDigest(final byte[] digest, final boolean isEncrypt) {
        PaddedBufferedBlockCipher encryptCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
        ParametersWithIV parameterIV = createCipherParametersForDigest(digest);
        encryptCipher.init(isEncrypt, parameterIV);
        return encryptCipher;
    }

    private ParametersWithIV createCipherParametersForDigest(final byte[] digest) {
        byte[] initializationVector = Arrays.copyOfRange(digest, digest.length - 16, digest.length);
        byte[] key = Arrays.copyOf(digest, 32);
        return new ParametersWithIV(new KeyParameter(key), initializationVector);
    }

    protected byte[] procesDataUsingRSA(final RSAEngine rsaEngine, final byte[] data) {
        byte[] output = new byte[0];
        int blockSize = rsaEngine.getInputBlockSize();
        for (int chunkPosition = 0; chunkPosition < data.length; chunkPosition += blockSize) {
            int chunkSize = Math.min(blockSize, data.length - chunkPosition);
            byte[] processed = rsaEngine.processBlock(data, chunkPosition, chunkSize);
            output = ArrayUtils.addAll(output, processed);
        }
        return output;
    }

}