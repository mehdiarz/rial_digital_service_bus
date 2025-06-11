package ir.sinatech.rial_digital_service_bus.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AESCipherUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

//    private static final String key = "1234567890samplekey1234567890123";
//    private static final String iv = "1234567890123456";

    private static  String key = "";
    private static  String iv = "";

    public AESCipherUtil(@Qualifier("encryptionConfiguration") EncryptionConfiguration config) {
        if (config.getKey() == null || config.getIv() == null) {
            throw new IllegalStateException("Encryption configuration not loaded!");
        }
        key = config.getKey();
        iv = config.getIv();
    }


    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decoded = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
