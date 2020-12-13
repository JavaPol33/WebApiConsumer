package service;

import http.HttpClient;

public class MathService {

    private final HttpClient httpClient;

    public MathService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String randomMath() {
        String uri = "http://numbersapi.com/random/math";
        return httpClient.get(uri);
    }

    public String math(int number) {
        String uri = "http://numbersapi.com/" + number;
        return httpClient.get(uri);
    }
}
