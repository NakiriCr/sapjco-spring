package org.yanzx.core.common.security.cipher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yanzx.core.common.security.cipher.algorithm.RSA;
import org.yanzx.core.common.security.cipher.trait.Cert;
import org.yanzx.core.common.security.semaphore.InvalidCipherTextSemaphore;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

import static java.util.Base64.getEncoder;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/23 15:11
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class RSACert implements Cert {

    private String cipher;

    private String publicKey;

    private String privateKey;

    public void assertVerify(String unVerifyKey) throws InvalidCipherTextSemaphore {
        if (!unVerifyKey.equals(RSA.decrypt(cipher, RSA.parseRSAPublicKey(publicKey)))) {
            throw new InvalidCipherTextSemaphore("Could not verify key: [" + unVerifyKey + "]."); }
    }

    /* ============================================================================================================= */

    public static String decrypt(String cipher, String publicKey) {
        return RSA.decrypt(cipher, RSA.parseRSAPublicKey(publicKey));
    }

    /* ============================================================================================================= */

    public static RSACert create(String plaintext) {
        return create(plaintext, RSA.generateKeyPair());
    }

    public static RSACert create(String plaintext, KeyPair keyPair) {
        return new RSACert(
                RSA.encrypt(plaintext, (RSAPrivateKey) keyPair.getPrivate()),
                getEncoder().encodeToString(keyPair.getPublic().getEncoded()),
                getEncoder().encodeToString(keyPair.getPrivate().getEncoded())
        );
    }
}
