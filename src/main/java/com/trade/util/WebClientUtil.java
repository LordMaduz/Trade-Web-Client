package com.trade.util;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;

@Component
public class WebClientUtil {
    private final WebClient webClient = WebClient.create("http://localhost:8070/api/cedar-agent/authorize");

    public WebClient.ResponseSpec getResponse(String principle, String action,
                                              String resource, String service,
                                              String context, String data, String check) throws URISyntaxException {
       return  webClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .header("data", data)
                .header("service-id", service)
                .header("security-group", principle)
                .header("action-id", action)
                .header("resource-id", resource)
                .header("context", context)
                .header("isColumnLevelValidation", check)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }
}
