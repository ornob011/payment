package com.dsi.rjsc.payment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PaymentConfig {

    @Value("${service.url.token}")
    private String serviceUrlToken;

    @Value("${post.data.token}")
    private String postDataToken;

    @Value("${service.url.ecomm}")
    private String serviceUrlEcomm;

    @Value("${payment.merchantId}")
    private String merchantId;

    @Value("${payment.amount}")
    private String amount;

    @Value("${payment.currency}")
    private String currency;

    @Value("${payment.description}")
    private String description;

    @Value("${payment.approveUrl}")
    private String approveUrl;

    @Value("${payment.cancelUrl}")
    private String cancelUrl;

    @Value("${payment.declineUrl}")
    private String declineUrl;

    @Value("${payment.userName}")
    private String userName;

    @Value("${payment.passWord}")
    private String passWord;

}