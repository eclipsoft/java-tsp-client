package com.eclipsoft.tsp;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.tsp.TSPAlgorithms;

public enum HashAlgorithm {

    MD5(TSPAlgorithms.MD5, "MD5"), SHA1(TSPAlgorithms.SHA1, "SHA1"), SHA256(TSPAlgorithms.SHA256, "SHA-256"), SHA384(TSPAlgorithms.SHA384, "SHA-384"), SHA512(TSPAlgorithms.SHA512, "SHA-512");

    private final ASN1ObjectIdentifier tspAlgorithm;

    private final String digestAlgorithm;

    HashAlgorithm(ASN1ObjectIdentifier tspAlgorithm, String digestAlgorithm) {
        this.tspAlgorithm = tspAlgorithm;
        this.digestAlgorithm = digestAlgorithm;
    }

    public ASN1ObjectIdentifier getTSPAlgorithm() {
        return this.tspAlgorithm;
    }

    public String getDigestAlgorithm() {
        return this.digestAlgorithm;
    }
}
