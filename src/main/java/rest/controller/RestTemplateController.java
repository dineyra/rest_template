package rest.controller;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import rest.entity.User;


public class RestTemplateController {

    final static String api = "http://94.198.50.185:7081/api/users";

    static RestTemplate restTemplate = new RestTemplate();

    static String resultHeader = "";

    public static void main(String[] args) {
        String cookie = getUsers();
        resultHeader += createUser(cookie);
        resultHeader += updateUser(cookie);
        resultHeader += deleteUser(cookie);
        System.out.println(resultHeader);
    }

    private static String getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(api,
                HttpMethod.GET, entity, String.class);
        String cookie = response.getHeaders().getFirst("Set-Cookie");
        return cookie;

    }

    private static String createUser(String cookie) {
        User user = new User(3L, "James", "Brown", (byte) 30);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(api,
                HttpMethod.POST, request, String.class);
        return response.getBody();
    }

    private static String updateUser(String cookie) {
        User updUser = new User(3L, "Thomas", "Shelby", (byte) 31);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<User> request = new HttpEntity<>(updUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(api,
                HttpMethod.PUT, request, String.class);
        return response.getBody();

    }

    private static String deleteUser(String cookie) {
        String url = api + "/3";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url,
                HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }
}
