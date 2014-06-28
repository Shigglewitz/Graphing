package org.shigglewitz.chess.controller;

import java.net.URL;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ControllerTestHelper {
    public static ResponseEntity<String> accessUrl(URL deploymentUrl,
            String path, HttpMethod method) {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(deploymentUrl.toString() + path,
                method, null, String.class);

        return response;
    }
}
