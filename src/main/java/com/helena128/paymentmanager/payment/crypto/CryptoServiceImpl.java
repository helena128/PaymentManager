package com.helena128.paymentmanager.payment.crypto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final StandardPBEStringEncryptor encryptor;

    public String encrypt(final String source) {
        return encryptor.encrypt(source);
    }

    public String decrypt(final String source) {
        return encryptor.decrypt(source);
    }

}
