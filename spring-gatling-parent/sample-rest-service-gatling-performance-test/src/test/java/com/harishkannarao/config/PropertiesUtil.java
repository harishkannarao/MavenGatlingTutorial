package com.harishkannarao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class PropertiesUtil {

    @Autowired
    @Value("${application.url}")
    private String applicationUrl;

    @Autowired
    @Value("${constant.users.per.second}")
    private String constantUsersPerSec;

    @Autowired
    @Value("${duration.in.minutes}")
    private String durationInMinutes;


    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getConstantUsersPerSec() {
        return constantUsersPerSec;
    }

    public void setConstantUsersPerSec(String constantUsersPerSec) {
        this.constantUsersPerSec = constantUsersPerSec;
    }

    public String getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(String durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
