package bg.sofia.uni.fmi.mjt.space.algorithm;

import bg.sofia.uni.fmi.mjt.space.exception.CipherException;

import java.io.InputStream;
import java.io.OutputStream;

public class Rijndael implements SymmetricBlockCipher {
    @Override
    public void encrypt(InputStream inputStream, OutputStream outputStream) throws CipherException {
        try {

        } catch (Exception e) {
            throw new CipherException("encrypt failed", e);
        }
    }

    @Override
    public void decrypt(InputStream inputStream, OutputStream outputStream) throws CipherException {
        try {

        } catch (Exception e) {
            throw new CipherException("decrypt failed", e);
        }
    }
}
