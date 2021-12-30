package com.eclipsoft.tsp.trx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import java.util.Date;

public final class TransactionGenerator {

    private static final Faker faker = new Faker();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Transaction generate() {
        Transaction transaction = new Transaction();
        transaction.setCode(faker.numerify("TRX-######"));
        transaction.setBeneficiaryId(faker.idNumber().valid());
        transaction.setBeneficiaryName(faker.name().name());
        transaction.setCellphone(faker.phoneNumber().cellPhone());
        transaction.setAmount(Double.valueOf(faker.numerify("####")));
        transaction.setPaymentDate(new Date());
        transaction.setAtm(faker.bothify("BCO-???-ATM-###"));

        return transaction;
    }

    public static byte[] generateAndWriteJsonValueAsBytes() throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(generate());
    }

    public static String generateAndWriteJsonValueAsString() throws JsonProcessingException {
        return objectMapper.writeValueAsString(generate());
    }
}
