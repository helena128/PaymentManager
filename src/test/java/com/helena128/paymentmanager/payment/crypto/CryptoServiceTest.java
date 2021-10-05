package com.helena128.paymentmanager.payment.crypto;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class CryptoServiceTest {

    @InjectMocks
    public CryptoServiceImpl cryptoServiceImpl;

    @BeforeEach
    public void setup() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("123");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        ReflectionTestUtils.setField(cryptoServiceImpl, "encryptor", encryptor);
    }

    @Test
    public void testEncryptAndDecrypt() {
        String testString = "TestString";
        String encryptionResult = cryptoServiceImpl.encrypt(testString);
        Assertions.assertNotEquals(testString, encryptionResult);
        String decryptionResult = cryptoServiceImpl.decrypt(encryptionResult);
        Assertions.assertEquals(testString, decryptionResult);
    }

}