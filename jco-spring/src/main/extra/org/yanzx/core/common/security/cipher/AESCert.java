package org.yanzx.core.common.security.cipher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yanzx.core.common.security.cipher.algorithm.AES;
import org.yanzx.core.common.security.cipher.trait.Cert;
import org.yanzx.core.common.security.semaphore.InvalidCipherTextSemaphore;

import java.util.UUID;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/23 15:10
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AESCert implements Cert {

    private String cipher;

    private String vector;

    public void assertVerify(String unVerifyKey) throws InvalidCipherTextSemaphore {
        if (!unVerifyKey.equals(AES.decrypt(cipher, vector))) {
            throw new InvalidCipherTextSemaphore("Could not verify key: [" + unVerifyKey + "]."); }
    }

    /* ============================================================================================================= */

    public static String decrypt(String cipher, String vector) {
        return AES.decrypt(cipher, vector);
    }

    /* ============================================================================================================= */

    public static AESCert create(String plaintext) {
        return create(plaintext, UUID.randomUUID().toString());
    }

    public static AESCert create(String plaintext, String vector) {
        return new AESCert(AES.encrypt(plaintext, vector), vector);
    }
}
