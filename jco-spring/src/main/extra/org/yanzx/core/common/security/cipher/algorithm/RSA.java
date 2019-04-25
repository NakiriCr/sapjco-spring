package org.yanzx.core.common.security.cipher.algorithm;

import org.yanzx.core.common.security.semaphore.*;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

/**
 * Description: Asymmetrical encrypt & decrypt.
 *
 * @author VirtualCry
 * @date 2019/2/21 21:14
 */
public class RSA {

    /* Encrypt algorithm - RSA. */
    private static final String KEY_ALGORITHM_RSA = "RSA";
    /* Key size. */
    private static final int KEY_SIZE = 512;

    /* Signature algorithm. */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";


    /**
     * Generate key pair.
     * @return KeyPair
     */
    public static KeyPair generateKeyPair() throws GenerateKeyPairOnErrorSemaphore {
        try {
            KeyPairGenerator _keyGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
            _keyGenerator.initialize(KEY_SIZE);

            return _keyGenerator.generateKeyPair();

        }catch (Exception _ex) {
            throw new GenerateKeyPairOnErrorSemaphore("Fail to generate key pair.", _ex); }
    }

    /* ============================================================================================================ */

    /**
     * Asymmetrical encrypt by private key.
     * @param _plaintext _plaintext
     * @param _privateKey _privateKey
     * @return encrypt str.
     * */
    public static String encrypt(String _plaintext, RSAPrivateKey _privateKey) throws EncryptPlaintextOnErrorSemaphore {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            PrivateKey _secretKey = _keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(_privateKey.getEncoded()));

            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.ENCRYPT_MODE, _secretKey);

            return getEncoder().encodeToString(
                    _cipher.doFinal(_plaintext.getBytes(UTF_8)));

        }catch (Exception _ex) {
            throw new EncryptPlaintextOnErrorSemaphore("Fail to encrypt.", _ex); }
    }

    /**
     * Asymmetrical decrypt by private key.
     * @param _cipherText _cipherText
     * @param _privateKey _privateKey
     * @return encrypt str.
     * */
    public static String decrypt(String _cipherText, RSAPrivateKey _privateKey) throws InvalidCipherTextSemaphore {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            PrivateKey _secretKey = _keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(_privateKey.getEncoded()));

            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.DECRYPT_MODE, _secretKey);

            return new String(
                    _cipher.doFinal(getDecoder().decode(_cipherText)), UTF_8);

        }catch (Exception _ex) {
            throw new InvalidCipherTextSemaphore("Fail to decrypt.", _ex); }
    }

    /**
     * Asymmetrical encrypt by public key.
     * @param _plaintext _plaintext
     * @param _publicKey _publicKey
     * @return encrypt str.
     * */
    public static String encrypt(String _plaintext, RSAPublicKey _publicKey) throws EncryptPlaintextOnErrorSemaphore {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            PublicKey _secretKey = _keyFactory.generatePublic(
                    new X509EncodedKeySpec(_publicKey.getEncoded()));

            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.ENCRYPT_MODE, _secretKey);

            return getEncoder().encodeToString(
                    _cipher.doFinal(_plaintext.getBytes(UTF_8)));

        }catch (Exception _ex) {
            throw new EncryptPlaintextOnErrorSemaphore("Fail to encrypt.", _ex); }
    }

    /**
     * Asymmetrical decrypt by public key.
     * @param _cipherText _cipherText
     * @param _publicKey _publicKey
     * @return encrypt str.
     * */
    public static String decrypt(String _cipherText, RSAPublicKey _publicKey) throws InvalidCipherTextSemaphore {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            PublicKey _secretKey = _keyFactory.generatePublic(
                    new X509EncodedKeySpec(_publicKey.getEncoded()));

            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.DECRYPT_MODE, _secretKey);

            return new String(
                    _cipher.doFinal(getDecoder().decode(_cipherText)), UTF_8);

        }catch (Exception _ex) {
            throw new InvalidCipherTextSemaphore("Fail to decrypt.", _ex); }
    }

    /* ============================================================================================================ */

    /**
     * Signature content.
     * @param _content _content
     * @param _privateKey _privateKey
     * @return signature str.
     */
    public static String signature(String _content, RSAPrivateKey _privateKey) throws SignatureTextOnErrorSemaphore {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            PrivateKey _secretKey = _keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(getDecoder().decode(_privateKey.getEncoded())));

            Signature _signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            _signature.initSign(_secretKey);
            _signature.update(_content.getBytes(UTF_8));

            return getEncoder().encodeToString(_signature.sign());

        }catch (Exception _ex) {
            throw new SignatureTextOnErrorSemaphore("Fail to signature content.", _ex); }
    }

    /**
     * Verify signature.
     * @param _content _content
     * @param  _publicKey _publicKey
     * @param _signatureStr _signatureStr
     */
    public static void verifySignature(String _content, RSAPublicKey _publicKey, String _signatureStr) throws InvalidSignatureSemaphore {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            PublicKey _secretKey = _keyFactory.generatePublic(
                    new X509EncodedKeySpec(_publicKey.getEncoded()));

            Signature _signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            _signature.initVerify(_secretKey);
            _signature.update(_content.getBytes(UTF_8));

            if (!_signature.verify(getDecoder().decode(_signatureStr))) throw new InvalidSignatureSemaphore();

        }catch (Exception _ex) {
            throw new InvalidSignatureSemaphore("Fail to verify signature.", _ex); }
    }

    /* ============================================================================================================= */

    /**
     * Parse str to RSAPublicKey obj,
     * @param _publicKey _publicKey
     */
    public static RSAPublicKey parseRSAPublicKey(String _publicKey) {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            return (RSAPublicKey) _keyFactory.generatePublic(
                    new X509EncodedKeySpec(getDecoder().decode(_publicKey.getBytes(UTF_8))));

        }catch (Exception _ex) {
            throw new GenerateKeyPairOnErrorSemaphore(_ex); }
    }

    /**
     * Parse str to RSAPrivateKey obj,
     * @param _privateKey _privateKey
     */
    public static RSAPrivateKey parseRSAPrivateKey(String _privateKey) {
        try {
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            return (RSAPrivateKey) _keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(getDecoder().decode(_privateKey.getBytes(UTF_8))));

        }catch (Exception _ex) {
            throw new GenerateKeyPairOnErrorSemaphore(_ex); }
    }
}
