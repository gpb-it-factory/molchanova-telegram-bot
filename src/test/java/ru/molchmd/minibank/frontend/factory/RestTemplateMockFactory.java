package ru.molchmd.minibank.frontend.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateMockFactory {
    private RestTemplateMockFactory() {
    }

    public static class RestTemplateRegisterCommand extends RestTemplate {
        @Override
        public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
            ResponseEntity<T> response;
            switch (url) {
                case "CREATED" -> response = new ResponseEntity<>(HttpStatus.CREATED);
                case "CONFLICT" -> response = new ResponseEntity<>(HttpStatus.CONFLICT);
                case "SERVICE_UNAVAILABLE" -> throw new ResourceAccessException("Server is not available");
                case "UNKNOWN_EXCEPTION" -> throw new RuntimeException("Something went wrong");
                default -> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return response;
        }
    }
}
