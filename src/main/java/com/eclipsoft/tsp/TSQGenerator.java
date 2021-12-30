package com.eclipsoft.tsp;

import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class TSQGenerator {

    public static TimeStampRequest generate(HashAlgorithm hashAlgorithm, byte[] message) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm.getDigestAlgorithm());
        byte[] hashedMessage = messageDigest.digest(message);

        TimeStampRequestGenerator tsqGenerator = new TimeStampRequestGenerator();
        tsqGenerator.setCertReq(true);
        return tsqGenerator.generate(hashAlgorithm.getTSPAlgorithm(), hashedMessage);
    }

}
