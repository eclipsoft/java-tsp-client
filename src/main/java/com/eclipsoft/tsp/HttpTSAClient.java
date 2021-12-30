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

import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpTSAClient {

    private final HttpClient httpClient;
    private final String tsaUrl;
    private final String tsaUsername;
    private final String tsaPassword;
    private final int tsaRequestTimeoutInSec;

    public HttpTSAClient(String tsaUrl, String tsaUsername, String tsaPassword, int tsaRequestTimeoutInSec) {
        this.httpClient = HttpClient.newHttpClient();
        this.tsaUrl = tsaUrl;
        this.tsaUsername = tsaUsername;
        this.tsaPassword = tsaPassword;
        this.tsaRequestTimeoutInSec = tsaRequestTimeoutInSec;
    }

    public TimeStampResponse sendTimeStampRequest(TimeStampRequest timeStampRequest) throws Exception {
        String credentials = this.tsaUsername + ":" + this.tsaPassword;

        HttpRequest httpRequest  = HttpRequest.newBuilder(new URI(this.tsaUrl))
                .header("Authorization", "Basic " + Utils.encodeToBase64(credentials.getBytes()))
                .header("Content-Type", "application/timestamp-query")
                .POST(HttpRequest.BodyPublishers.ofByteArray(timeStampRequest.getEncoded()))
                .timeout(Duration.ofSeconds(this.tsaRequestTimeoutInSec))
                .build();

        HttpResponse<byte[]> response = this.httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() != 200) {
            throw new Exception("TSA responded with an unexpected statusCode: " + response.statusCode());
        }
        return new TimeStampResponse(response.body());
    }

}
