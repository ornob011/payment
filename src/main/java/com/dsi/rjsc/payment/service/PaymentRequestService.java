package com.dsi.rjsc.payment.service;

import com.dsi.rjsc.payment.factory.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class PaymentRequestService {

    @Value("${pkcs12.base64}")
    private String pkcs12Base64;

    @Value("${pkcs12.password}")
    private String pkcs12Password;

    @Autowired
    private RestTemplateFactory restTemplateFactory;

    public Map<String, Object> processRequest(String requestBody, String serviceUrl) {
        try {
            RestTemplate restTemplate = restTemplateFactory.createRestTemplate(pkcs12Base64, pkcs12Password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Map.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to make the request: ", e);
        }
    }
}
