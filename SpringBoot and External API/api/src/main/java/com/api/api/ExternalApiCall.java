package com.api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExternalApiCall {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/restTemplate/get")
    public ResponseEntity<String> getJsonData() {
        String url = "https://jsonplaceholder.typicode.com/posts/";
        return restTemplate.getForEntity(url, String.class);
    }
    
    @PostMapping("/restTemplate/post")
    public String postJsonData(@RequestBody String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", request, String.class);
        return response.getBody();
    }
    
    @DeleteMapping("/restTemplate/delete/{id}")
    public String deleteJsonData(@PathVariable String id) {
        String url = "https://jsonplaceholder.typicode.com/posts/"+id;
        restTemplate.delete(url);
        return id+" is delete";
    }
    
    
}
