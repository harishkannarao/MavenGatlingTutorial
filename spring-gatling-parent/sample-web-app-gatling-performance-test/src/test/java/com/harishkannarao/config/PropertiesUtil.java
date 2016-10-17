package com.harishkannarao.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private final String webApplicationUrl;

    private final String basicCrudWebScenarioPerSecond;
    private final String basicCrudWebScenarioDurationInSecond;

    private final Properties testConfig;
    private final Properties profileConfig;

    public PropertiesUtil() {
        String targetEnvironment = System.getProperty("targetEnvironment", "local");
        String profile = System.getProperty("profile", "sanity");

        testConfig = new Properties();
        InputStream tcInputStream = this.getClass().getResourceAsStream("/properties/"+targetEnvironment+"-test-config.properties");
        profileConfig = new Properties();
        InputStream profileInputStream = this.getClass().getResourceAsStream("/properties/"+targetEnvironment+"-"+profile+".properties");
        try {
            testConfig.load(tcInputStream);
            tcInputStream.close();
            profileConfig.load(profileInputStream);
            profileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        webApplicationUrl = testConfig.getProperty("web.application.url");

        basicCrudWebScenarioPerSecond = profileConfig.getProperty("basic.crud.web.scenario.per.second");
        basicCrudWebScenarioDurationInSecond = profileConfig.getProperty("basic.crud.web.scenario.duration.in.second");

    }

    public String getWebApplicationUrl() {
        return webApplicationUrl;
    }

    public String getBasicCrudWebScenarioPerSecond() {
        return basicCrudWebScenarioPerSecond;
    }

    public String getBasicCrudWebScenarioDurationInSecond() {
        return basicCrudWebScenarioDurationInSecond;
    }

}
