package com.harishkannarao.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private final String webApplicationUrl;
    private final String noOfRequestsPerSecond;
    private final String totalDurationInSeconds;

    private final Properties testConfig;

    public PropertiesUtil() {
        String targetEnvironment = System.getProperty("targetEnvironment", "local");

        testConfig = new Properties();
        InputStream tcInputStream = this.getClass().getResourceAsStream("/properties/"+targetEnvironment+"-test-config.properties");
        try {
            testConfig.load(tcInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            tcInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        webApplicationUrl = testConfig.getProperty("web.application.url");
        noOfRequestsPerSecond = testConfig.getProperty("no.of.requests.per.second");
        totalDurationInSeconds = testConfig.getProperty("total.duration.in.seconds");
    }

    public String getWebApplicationUrl() {
        return webApplicationUrl;
    }

    public String getNoOfRequestsPerSecond() {
        return noOfRequestsPerSecond;
    }

    public String getTotalDurationInSeconds() {
        return totalDurationInSeconds;
    }
}
