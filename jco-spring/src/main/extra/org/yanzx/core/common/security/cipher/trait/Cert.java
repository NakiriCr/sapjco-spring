package org.yanzx.core.common.security.cipher.trait;

import org.yanzx.core.common.security.semaphore.InvalidCipherTextSemaphore;

import java.io.Serializable;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/5/9 18:14
 */
public interface Cert extends Serializable {

    /**
     * Verify key.
     * @param _unVerifyKey _unVerifyKey
     */
    void assertVerify(String _unVerifyKey) throws InvalidCipherTextSemaphore;
}
