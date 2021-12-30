/*
 * The MIT License
 *
 * Copyright 2021 Eclipsoft S.A.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.eclipsoft.tsp;

import com.eclipsoft.tsp.config.ConfigPropertiesManager;
import com.eclipsoft.tsp.config.TSPClientProperties;
import com.eclipsoft.tsp.trx.TransactionGenerator;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSPClientApp {

    private static final Logger logger = LoggerFactory.getLogger(TSPClientApp.class);

    public static void main(String[] args) throws Exception {
        TSPClientProperties tspClientProperties = ConfigPropertiesManager.loadTSPClientProperties();

        HttpTSAClient httpTSAClient = new HttpTSAClient(
                tspClientProperties.getTsaUrl(),
                tspClientProperties.getTsaUsername(),
                tspClientProperties.getTsaPassword(),
                tspClientProperties.getTsaRequestTimeoutInSec()
        );

        byte[] transactionAsBytes = TransactionGenerator.generateAndWriteJsonValueAsBytes();
        TimeStampRequest timeStampRequest = TSQGenerator.generate(HashAlgorithm.SHA256, transactionAsBytes);

        TimeStampResponse timeStampResponse = httpTSAClient.sendTimeStampRequest(timeStampRequest);
        logger.info("Timestamp response status: {}", timeStampResponse.getStatus());
        logger.info("Timestamp response TSA: {}", timeStampResponse.getTimeStampToken().getTimeStampInfo().getTsa());
    }
}
