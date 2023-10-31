package com.dsi.rjsc.payment.controller;

import com.dsi.rjsc.payment.service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;


@Controller
public class PaymentController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Value("${pkcs12.base64}")
    private String pkcs12Base64;

    @Value("${pkcs12.password}")
    private String pkcs12Password;

    @Value("${service.url.token}")
    private String serviceUrlToken;

    @Value("${post.data.token}")
    private String postDataToken;

    @Value("${service.url.ecomm}")
    private String serviceUrlEcomm;

    @GetMapping("/")
    public String index(Model model) {
        Map<String, Object> tokenData = paymentRequestService.processRequest(postDataToken, serviceUrlToken);
        String transactionId = (String) tokenData.get("transactionId");

        String postDataEcomm = String.format("{\"merchantId\": \"11122333\", \"amount\": \"100\", \"currency\": \"050\", \"description\": \"Reference_Number\", \"approveUrl\": \"http://localhost:8080/CityBankPHP_1.0.1/Approved.php\", \"cancelUrl\": \"http://localhost:8080/CityBankPHP_1.0.1/Cancelled.php\", \"declineUrl\": \"http://localhost:8080/CityBankPHP_1.0.1/Declined.php\", \"userName\": \"test\", \"passWord\": \"123456Aa\", \"secureToken\": \"%s\"}", transactionId);
        Map<String, Object> ecommData = paymentRequestService.processRequest(postDataEcomm, serviceUrlEcomm);

        Map<String, Object> items = (Map<String, Object>) ecommData.get("items");
        String redirectUrl = items.get("url") + "?ORDERID=" + items.get("orderId") + "&SESSIONID=" + items.get("sessionId");

        model.addAttribute("redirectUrl", redirectUrl);

        return "redirect";
    }
}
