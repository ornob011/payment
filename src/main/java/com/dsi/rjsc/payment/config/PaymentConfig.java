package com.dsi.rjsc.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

    public String getServiceUrlToken() {
        return serviceUrlToken;
    }

    public String getPostDataToken() {
        return postDataToken;
    }

    public String getServiceUrlEcomm() {
        return serviceUrlEcomm;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getApproveUrl() {
        return approveUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public String getDeclineUrl() {
        return declineUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}