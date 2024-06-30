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
                default -> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response;
        }
    }

    public static class RestTemplateCreateAccountCommand extends RestTemplate {
        @Override
        public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
            ResponseEntity<T> response;
            switch (url) {
                case "CREATED" -> response = new ResponseEntity<>(HttpStatus.CREATED);
                case "BAD_REQUEST" -> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                case "CONFLICT" -> response = new ResponseEntity<>(HttpStatus.CONFLICT);
                case "SERVICE_UNAVAILABLE" -> throw new ResourceAccessException("Server is not available");
                case "UNKNOWN_EXCEPTION" -> throw new RuntimeException("Something went wrong");
                default -> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response;
        }
    }

    public static class RestTemplateCurrentBalanceCommand extends RestTemplate {
        private final String jsonResponse;
        public RestTemplateCurrentBalanceCommand(String jsonResponse) {
            this.jsonResponse = jsonResponse;
        }

        @Override
        public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
            ResponseEntity<T> response;
            switch (url) {
                case "OK" -> response = new ResponseEntity<>((T) jsonResponse, HttpStatus.OK);
                case "BAD_REQUEST" -> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                case "SERVICE_UNAVAILABLE" -> throw new ResourceAccessException("Server is not available");
                case "UNKNOWN_EXCEPTION" -> throw new RuntimeException("Something went wrong");
                default -> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response;
        }
    }

    public static class RestTemplateTransferCommand extends RestTemplate {
        private final String jsonResponse;
        public RestTemplateTransferCommand(String jsonResponse) {
            this.jsonResponse = jsonResponse;
        }

        @Override
        public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
            ResponseEntity<T> response;
            switch (url) {
                case "OK" -> response = new ResponseEntity<>(HttpStatus.OK);
                case "BAD_REQUEST" -> response = new ResponseEntity<>((T) jsonResponse, HttpStatus.BAD_REQUEST);
                case "SERVICE_UNAVAILABLE" -> throw new ResourceAccessException("Server is not available");
                case "UNKNOWN_EXCEPTION" -> throw new RuntimeException("Something went wrong");
                default -> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response;
        }
    }
}
