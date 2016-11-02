package com.harishkannarao.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private final String applicationUrl;

    private final String basicCrudRestScenarioPerSecond;
    private final String basicCrudRestScenarioDurationInSecond;
    private final String sumCalculatorStaticFeederScenarioPerSecond;
    private final String sumCalculatorStaticFeederScenarioDurationInSecond;
    private final String sumCalculatorJsonFeederScenarioPerSecond;
    private final String sumCalculatorJsonFeederScenarioDurationInSecond;
    private final String sumCalculatorDynamicFeederScenarioPerSecond;
    private final String sumCalculatorDynamicFeederScenarioDurationInSecond;

    private final Properties testConfig;
    private final Properties profileConfig;

    private static class SingletonHelper {
        private static final PropertiesUtil INSTANCE = new PropertiesUtil();
    }

    public static PropertiesUtil getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private PropertiesUtil() {
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

        applicationUrl = testConfig.getProperty("application.url");
        basicCrudRestScenarioPerSecond = profileConfig.getProperty("basic.crud.rest.scenario.per.second");
        basicCrudRestScenarioDurationInSecond = profileConfig.getProperty("basic.crud.rest.scenario.duration.in.second");
        sumCalculatorStaticFeederScenarioPerSecond = profileConfig.getProperty("sum.calculator.static.feeder.scenario.per.second");
        sumCalculatorStaticFeederScenarioDurationInSecond = profileConfig.getProperty("sum.calculator.static.feeder.scenario.duration.in.second");
        sumCalculatorJsonFeederScenarioPerSecond = profileConfig.getProperty("sum.calculator.json.feeder.scenario.per.second");
        sumCalculatorJsonFeederScenarioDurationInSecond = profileConfig.getProperty("sum.calculator.json.feeder.scenario.duration.in.second");
        sumCalculatorDynamicFeederScenarioPerSecond = profileConfig.getProperty("sum.calculator.dynamic.feeder.scenario.per.second");
        sumCalculatorDynamicFeederScenarioDurationInSecond = profileConfig.getProperty("sum.calculator.dynamic.feeder.scenario.duration.in.second");
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public String getBasicCrudRestScenarioPerSecond() {
        return basicCrudRestScenarioPerSecond;
    }

    public String getBasicCrudRestScenarioDurationInSecond() {
        return basicCrudRestScenarioDurationInSecond;
    }

    public String getSumCalculatorStaticFeederScenarioPerSecond() {
        return sumCalculatorStaticFeederScenarioPerSecond;
    }

    public String getSumCalculatorStaticFeederScenarioDurationInSecond() {
        return sumCalculatorStaticFeederScenarioDurationInSecond;
    }

    public String getSumCalculatorJsonFeederScenarioPerSecond() {
        return sumCalculatorJsonFeederScenarioPerSecond;
    }

    public String getSumCalculatorJsonFeederScenarioDurationInSecond() {
        return sumCalculatorJsonFeederScenarioDurationInSecond;
    }

    public String getSumCalculatorDynamicFeederScenarioPerSecond() {
        return sumCalculatorDynamicFeederScenarioPerSecond;
    }

    public String getSumCalculatorDynamicFeederScenarioDurationInSecond() {
        return sumCalculatorDynamicFeederScenarioDurationInSecond;
    }
}
