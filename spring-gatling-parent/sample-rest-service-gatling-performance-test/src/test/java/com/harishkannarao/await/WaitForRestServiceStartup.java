package com.harishkannarao.await;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;

public class WaitForRestServiceStartup {

    private static final String REST_SERVICE_PING_URL = "http://localhost:9000/ping.txt";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        given().ignoreExceptions().await().atMost(120, SECONDS).until(() -> {
            ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_PING_URL, HttpMethod.GET, null, String.class);
            assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        });
    }
}
