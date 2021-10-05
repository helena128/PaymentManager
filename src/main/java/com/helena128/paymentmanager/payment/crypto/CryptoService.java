package com.helena128.paymentmanager.payment.crypto;

public interface CryptoService {

    String encrypt(String source);
    String decrypt(String source);
}
