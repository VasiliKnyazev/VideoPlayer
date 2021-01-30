package com.luxoft.videoplayer.controller.rest;

import com.luxoft.videoplayer.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    void testAddUserSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/rest/admin/users";
        URI uri = new URI(baseUrl);
        User user = new User(1, "John Doe", "thingy", "123456", "test1@email.com");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    void testAddUserMissingHeader() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/rest/admin/users";
        URI uri = new URI(baseUrl);
        User user = new User(2, "Jane Doe", "doohickey", "123456", "test2@email.com");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        try {
            restTemplate.postForEntity(uri, request, String.class);
        } catch(HttpClientErrorException ex) {
            Assertions.assertEquals(400, ex.getRawStatusCode());
            Assertions.assertTrue(ex.getResponseBodyAsString().contains("Missing request header"));
        }
    }

    @Test
    void testGetUserListSuccessWithHeaders() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/rest/admin/users";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-LOCATION", "USA");

        HttpEntity<User> requestEntity = new HttpEntity<>(null, headers);

        try {
            restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        } catch(HttpClientErrorException ex) {
            Assertions.assertEquals(400, ex.getRawStatusCode());
            Assertions.assertTrue(ex.getResponseBodyAsString().contains("Missing request header"));
        }
    }

}
