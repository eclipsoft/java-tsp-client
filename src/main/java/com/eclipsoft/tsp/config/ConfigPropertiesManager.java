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
package com.eclipsoft.tsp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;



public final class ConfigPropertiesManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigPropertiesManager.class);

    private static final String CONF_FILE = "application.properties";

    private static final String TSA_URL_PROP = "tsa.url";
    private static final String TSA_USERNAME_PROP = "tsa.username";
    private static final String TSA_PASSWORD_PROP = "tsa.password";
    private static final String TSA_REQUEST_TIMEOUT_PROP = "tsa.request-timeout-in-seconds";

    private static final int DEFAULT_REQUEST_TIMEOUT = 20;

    private static Properties loadPropertiesFile() throws IOException {
        try (InputStream propertiesInputStream = ConfigPropertiesManager.class.getClassLoader().getResourceAsStream(CONF_FILE)) {
            Properties properties = new Properties();
            properties.load(propertiesInputStream);
            logger.info("Configuration properties file: {} was successfully uploaded.", CONF_FILE);
            return properties;
        } catch (IOException ex) {
            logger.error("Configuration properties file: {} could not be loaded. ", CONF_FILE, ex);
            throw ex;
        }
    }

    public static TSPClientProperties loadTSPClientProperties() throws IOException {
        Properties properties = loadPropertiesFile();

        String tsaUrl = Objects.requireNonNull(properties.getProperty(TSA_URL_PROP), nonNullMsg(TSA_URL_PROP));
        String tsaUsername = Objects.requireNonNull(properties.getProperty(TSA_USERNAME_PROP), nonNullMsg(TSA_USERNAME_PROP));
        String tsaPassword = Objects.requireNonNull(properties.getProperty(TSA_PASSWORD_PROP), nonNullMsg(TSA_PASSWORD_PROP));
        int tsaRequestTimeoutInSec;
        try {
            tsaRequestTimeoutInSec = Integer.parseInt(properties.getProperty(TSA_REQUEST_TIMEOUT_PROP));
        } catch (NumberFormatException ex) {
            tsaRequestTimeoutInSec = DEFAULT_REQUEST_TIMEOUT;
        }

        return new TSPClientProperties(tsaUrl, tsaUsername, tsaPassword, tsaRequestTimeoutInSec);
    }

    private static String nonNullMsg(String property) {
        return "Configuration property: " + property + " must not be null";
    }
}
