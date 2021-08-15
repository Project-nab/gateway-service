package com.icomercel.shopping.gateway.controller;

import com.icomercel.shopping.gateway.payload.GetTokenResponse;
import groovy.util.logging.Slf4j;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class Oauth2Test {
    @LocalServerPort
    public int port;

    @Test
    public void whenRequestWithAccessToken_thenReturnResult() {
        // When
        String accessToken = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = "http://localhost:" + port + "/users";
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                List.class);

        // Then
        assertEquals(2, response.getBody().size());
    }

    @Test(expected = HttpClientErrorException.Unauthorized.class)
    public void whenRequestWithouAccessToken_thenThrowException() {
        // When
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = "http://localhost:" + port + "/users";
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                List.class);
    }

    private String getAccessToken() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "password");
        param.add("redirect_uri", "com.okta.dev-56264046:/callback");
        param.add("username", "baonc93@gmail.com");
        param.add("password", "Abc13579");
        param.add("scope", "openid");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("0oa1gwoi7eoayeA7N5d7", "XahfCL7_EA4rkJmOeJZVjFxRo4Y6sme-l6GzD9Zg");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(param, headers);
        GetTokenResponse getTokenResponse = restTemplate.postForObject("https://dev-56264046.okta.com/oauth2/default/v1/token",
                entity, GetTokenResponse.class);
        return getTokenResponse.getAccess_token();
    }
}
