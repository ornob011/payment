package com.dsi.rjsc.payment.service;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Map;

@Service
public class PaymentRequestService {

    public Map<String, Object> processRequest(String requestBody, String serviceUrl, String pkcs12Base64, String pkcs12Password) {
        try {
            // Decode the Base64 encoded PKCS12 keystore content
            byte[] pkcs12Data = Base64.getDecoder().decode(pkcs12Base64);

            // Load the PKCS12 keystore from a byte array
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new ByteArrayInputStream(pkcs12Data), pkcs12Password.toCharArray());

            // Allow self-signed certificate
            SSLContext sslContext = SSLContextBuilder
                    .create()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .loadKeyMaterial(keyStore, pkcs12Password.toCharArray())
                    .build();

            // Disable hostname verification
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Build HttpClient with custom SSL context
            SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                    .setSslContext(sslContext)
                    .setHostnameVerifier(allHostsValid)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(
                            PoolingHttpClientConnectionManagerBuilder.create()
                                    .setSSLSocketFactory(sslSocketFactory)
                                    .build()
                    )
                    .build();

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

            RestTemplate restTemplate = new RestTemplate(factory);

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
