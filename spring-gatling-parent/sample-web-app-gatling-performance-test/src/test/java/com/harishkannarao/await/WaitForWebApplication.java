package com.harishkannarao.await;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;

public class WaitForWebApplication {
    private static final String WEB_APPLICATION_PING_URL = "http://localhost:9100/ping.txt";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            given().ignoreExceptions().await().atMost(120, SECONDS).until(() -> {
                ResponseEntity<String> response = restTemplate.exchange(WEB_APPLICATION_PING_URL, HttpMethod.GET, null, String.class);
                assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
