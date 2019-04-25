package org.yanzx.core.common.security.cipher.algorithm;

import org.yanzx.core.common.security.semaphore.EncryptPlaintextOnErrorSemaphore;
import org.yanzx.core.common.security.semaphore.InvalidCipherTextSemaphore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

/**
 * Description: Symmetric encrypt & decrypt.
 *
 * @author VirtualCry
 * @date 2019/2/21 20:25
 */
public class AES {

    /* Encrypt algorithm - AES. */
    private static final String KEY_ALGORITHM_AES = "AES";
    /* Key size. */
    private static final int KEY_SIZE = 128;

    /**
     * Symmetric encrypt.
     * @param _plaintext _plaintext
     * @param _vector _vector
     * @return encrypt str.
     */
    public static String encrypt(String _plaintext, String _vector){
        try {
            SecretKey _secretKey = createSecretKey(_vector);

            Cipher _cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            _cipher.init(Cipher.ENCRYPT_MODE, _secretKey);

            return getEncoder().encodeToString(
                    _cipher.doFinal(_plaintext.getBytes(UTF_8)));

        }catch (Exception _ex) {
            throw new EncryptPlaintextOnErrorSemaphore("Fail to encrypt plaintext.", _ex); }
    }

    /**
     * Symmetric decrypt.
     * @param _cipherText _cipherText
     * @param _vector _vector
     * @return decrypt str.
     */
    public static String decrypt(String _cipherText, String _vector) {
        try {
            SecretKey _secretKey = createSecretKey(_vector);

            Cipher _cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            _cipher.init(Cipher.DECRYPT_MODE, _secretKey);

            return new String(
                    _cipher.doFinal(getDecoder().decode(_cipherText)), UTF_8);

        }catch (Exception _ex) {
            throw new InvalidCipherTextSemaphore("Fail to decrypt key.", _ex); }
    }

    /**
     * Create secret key.
     * @param _vector _vector
     */
    private static SecretKey createSecretKey(String _vector) throws NoSuchAlgorithmException {
        KeyGenerator _keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM_AES);

        SecureRandom _random = SecureRandom.getInstance("SHA1PRNG");
        _random.setSeed(_vector.getBytes());

        _keyGenerator.init(KEY_SIZE, _random);

        return new SecretKeySpec(_keyGenerator.generateKey().getEncoded(), KEY_ALGORITHM_AES);
    }
}
