package com.harishkannarao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class PropertiesUtil {

    @Autowired
    @Value("${web.application.url}")
    private String webApplicationUrl;

    @Autowired
    @Value("${no.of.requests.per.second}")
    private String noOfRequestsPerSecond;

    @Autowired
    @Value("${total.duration.in.seconds}")
    private String totalDurationInSeconds;


    public String getWebApplicationUrl() {
        return webApplicationUrl;
    }

    public void setWebApplicationUrl(String webApplicationUrl) {
        this.webApplicationUrl = webApplicationUrl;
    }

    public String getNoOfRequestsPerSecond() {
        return noOfRequestsPerSecond;
    }

    public void setNoOfRequestsPerSecond(String noOfRequestsPerSecond) {
        this.noOfRequestsPerSecond = noOfRequestsPerSecond;
    }

    public String getTotalDurationInSeconds() {
        return totalDurationInSeconds;
    }

    public void setTotalDurationInSeconds(String totalDurationInSeconds) {
        this.totalDurationInSeconds = totalDurationInSeconds;
    }
}
