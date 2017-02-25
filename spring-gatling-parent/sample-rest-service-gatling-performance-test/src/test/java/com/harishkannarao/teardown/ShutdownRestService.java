package com.harishkannarao.teardown;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class ShutdownRestService {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:9000/shutdown", HttpMethod.POST, null, String.class);
    }
}
