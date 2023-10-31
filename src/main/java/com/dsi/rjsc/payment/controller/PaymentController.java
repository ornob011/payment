package com.dsi.rjsc.payment.controller;

import com.dsi.rjsc.payment.service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @GetMapping("/")
    public String index(Model model) {
        // Replace the following with actual Base64 encoded .p12 content and password
        String pkcs12Base64 = "MIIKTwIBAzCCCgUGCSqGSIb3DQEHAaCCCfYEggnyMIIJ7jCCBGIGCSqGSIb3DQEHBqCCBFMwggRPAgEAMIIESAYJKoZIhvcNAQcBMFcGCSqGSIb3DQEFDTBKMCkGCSqGSIb3DQEFDDAcBAhVTWk8Q4nLmgICCAAwDAYIKoZIhvcNAgkFADAdBglghkgBZQMEASoEEIrmTMj57rNAMSrzPrDt7iuAggPgohSrW0TvOaByIT9XbKQxLSPsi3sgdMnpDlSr0ZcBDgFhC50Y+rFaaxnBrPxqcg6K6A5CvahM47vmcdiA3TNSBqr0vZmuTB+NusRbDMotymixyHL5D0kUR82JsDSs4z+q0aTwlEZ/LskZ8uw53kx+NjwyyaPOcXF/pmWf607oPIgRZPiKecylpxzIG2oSyXGsu8vyDfhrLoDXNAM0mHC+yPwr8QL1QdZOmbgBubtWOzk8Q38qm8LK3hRz+7rLpni7Pk7YFUDAvQ7+ep2keYhGQFuDbhnTGD7S13i4wjqaAisEpQkfcSaLgTCVgTlDH0Bu//N5SLYb6rUlI5Lhr24tmk8k272CNyZpYnOaa3zr4Y9IxygyOv1z77NvTw4xi94Wk9X7hNsMkSydKWSvZ10iiv7GDSnhT8yfNFzQfwcpYnrg+AX53IwWevJahgIPm9839IPaUxVT9zsxJviVlWKx/fgTpUWW0mo943d73MDp8d2wq8akxdQmHrXCPZ8quXhRSHO7/bYUNLqXqThgt4Cz2UGtUUbALUPmbz7iiOBi8fVIArK5Uu1p5C8SAW6CpMpAxetk4MIAubXs0jbclYYk+HrpWM1Y1doVF6/L2GQkEqupOPwc6kCI/EFJIqEw9Oq6SXLAWDGFjIIgDm2AFmYJEA3H6L9EIpoWpqyuUl+amGHzXOn7AL9sDWaS1recp0KV0jbyJfh1s7HZZ6w5/fAVUGUFyhPomBhFQDlrKGR/AYEh/sG1i+wvyY8sL3UlH3p0olv25zyUN0TSxnZtV8uz7g0JmGK4pwGMgg9TLqgpBGpgVNnIdGSBeaOTVX7NSkiVpa5IyasR/wT0JCYZiRGQzVbOgSWkpEFDbLBIPxVMrHWO88CXhb4LFpTMthUwigrEgWg5R7V++I83vGhFE+WHk4eAri67Kx8H4d5sfrI87oj7f1iEQflxtAMmxrg8VY4+qk7hvqxGKrCRlkRlyFIDqVVgtH78WhmGftCCdcVjMmwybCtj+pTiEZKx89zFhnW8GZt/F1iOQc5Jiu1K9J3OVjzepHET8mGKMl76wjsVDkW3ASVpM6mzIFeIesVqke3hKVno9IorIr97Po9DlB8VQ7oW1KhwFU/UCO6zt/VvTqeWmxR4oKf+b4YaQD457sAfbcKtghLIBacR74lmD7Ri24UtYBMVcPqcjfLhIvwuppqM+7fy58Vd7PmsSfEwGb72dQEz5pWiRsaXSPIIIGTVTzWzCeUMg2F+Ammz8D0Qvt8zh3m1LDuKKIXOV/9VD7GJKUM+6HSwhq/2jD1SEcWwInMi4zWANTTDOEP58CeNg9MwggWEBgkqhkiG9w0BBwGgggV1BIIFcTCCBW0wggVpBgsqhkiG9w0BDAoBAqCCBTEwggUtMFcGCSqGSIb3DQEFDTBKMCkGCSqGSIb3DQEFDDAcBAjwQnV2n2mDUgICCAAwDAYIKoZIhvcNAgkFADAdBglghkgBZQMEASoEEOs0upmx3jKcxT0IMWXfm/QEggTQYTM2kbkb4AR+ov7wY9gGcuYnyUtWBCDbplbnRtNARhQRqIRQkKNCkpduXjndex5we+zx9l2hC5RNVucxel8W6xqJCeJnKcq6abIKxDQllv7LJIlc0HrA4JQQJvMWU4s9069sjqR+TZiKUULDaeYPSRJf0Z1NLeJ9hmgczwM4rTCe+kssxolRZ213sqHwCEpJnUjNwqkjS1gF+DLwv/jop5tuvJXLE2t56nTeQAXxRfNSKtR3hLwYXh/aGrR+o2ddfbOHI3D2yNkY84Lgr7gszXHaVtSux4RmP2qpFuZgVEZXfizp7CgzCjuOhqHwGL7zKKTFSVNy2KjMqA1EKOizoaPvYfhc3eJ45yzQ9Egomf2mYuMTEzjiVaQsrEXSFNy79bFOsjxoG/rsEMIsWZhqCDtAfSibRjlVgZlh7inyvATBl0HPn0XB9MZhhwVntaAh10afY2Ppf1W2vDGW8IBATOgstxrXnJSVCHqDLGu55Mmdbzvvf4MGjlPYuCvRYqwbTUa7+orRHCgoghEClZe4lx/SPISLfrM0x8aQNwCkEh2aQP8GCiT6+EaIE3JxjTvM8sbWtO2SQUtAl+2AKHzxC3dAiIyo0KBM1xiEvmP8ZQ+W3goTtJnwePtjIqYMZZNypKi1nyI/b++Zex6mY34eLAKWaPj9AvhjLI2L42HFGeacU+fgl4Ohtj2uaXHr7fv0mwAp7ho0qszKpBuD8XKwcJCFDtJdEAXfJIPN8DmRMmgkXWhi7W/RPjGfGkX65b1X1i95gi9D9pGNHiC1QPu3a+UdwPQaL3roteu4IhODHRDdQ7YlKzSbPOkiXURgTUmY6lUktBUfzkoalZRkqYEhCpRJMsDCeKgMdXvoMxT1Px/1Sa/DbMYfyPLHq0/pLauZ1zVKdcY2wm6V2kfTqT26q2230LUZuZnk8bDF07qCvWfDuv0AV5IqWd3CVmcUT3jXqIgaDuqzg/v3n8IfKO82uAW0FUzL+xr4U7xKXx9t3YSHc60h5IG0C7riP9F8m+P1KouPM9tw4dTlW7fG3pph4TkhIki/KXXrBsM89zNxpYTKFLIab+Hl45ndcYq/vudgANN8D8M2hYoLME7YW6iHmnPZPdDh5CRz4WMeIfzz12Fn21zhaBLhRjIJ26TEnZt8Co8ntp1JG5SDj1+/yqQGLw4dFzHT1UelDASt0CW3+cvUXfimpzI24RrAlAtO9/vu0p5x4t/YxerDy6JMfqldFy+fyptvDso+GP/WuGiv3w5DjSXNY49TXhAEYNzpo96ccuwHGJ2zBCZddhrEH6mUupDvIAVtfNROHeH2v+iYukRFjjmm37X1vq+24Psr1CbuhAutJr101hfe3SyEVAXuEHRKm4s1hZd8izxPA5V6iIOqaEb6Jgd5osBINGYpgbUlexGnFR1tyYYWm2jhBoYTkcGzHjjZ+cczoxynC56Wpqm8/bmU/PLZ8qkq/L6+ysgSj6e+rOGhkzHVPsV6ZHPqV+mt5Hs923I9dweFaEmp/NuO8AHQWzKe088kJb1vYXe25HnAjvQ7Fp62ZenCqYQoBip4K7Qre0oMn6lafPXwc/cHkPRffUUE2CiQMFO+oH62hvWNDV724TfXeJhkLvXljh89raqpG8d2BnbhrvKE6SUxJTAjBgkqhkiG9w0BCRUxFgQUsulpckSDhnQhOmS4X0gTkhlxYZwwQTAxMA0GCWCGSAFlAwQCAQUABCAGw6MsjW/i+3D2k4w2jUatbzfJ2jGJ37DxHWndGqoYYQQIfscM7YMPhB8CAggA";
        String pkcs12Password = "password";

        String serviceUrltoken = "https://sandbox.thecitybank.com:7788/transaction/token";
        String postDatatoken = "{\"password\": \"123456Aa\", \"userName\": \"test\"}";

        Map<String, Object> cblcz = paymentRequestService.processRequest(postDatatoken, serviceUrltoken, pkcs12Base64, pkcs12Password);

        String transactionId = (String) cblcz.get("transactionId");

        String serviceUrlEcomm = "https://sandbox.thecitybank.com:7788/transaction/createorder";
        String postdataEcomm = String.format("{\"merchantId\": \"11122333\", \"amount\": \"100\", \"currency\": \"050\", \"description\": \"Reference_Number\", \"approveUrl\": \"http://localhost:8080/CityBankPHP_1.0.1/Approved.php\", \"cancelUrl\": \"http://localhost:8080/CityBankPHP_1.0.1/Cancelled.php\", \"declineUrl\": \"http://localhost:8080/CityBankPHP_1.0.1/Declined.php\", \"userName\": \"test\", \"passWord\": \"123456Aa\", \"secureToken\": \"%s\"}", transactionId);

        Map<String, Object> cblEcomm = paymentRequestService.processRequest(postdataEcomm, serviceUrlEcomm, pkcs12Base64, pkcs12Password);

        Map<String, Object> items = (Map<String, Object>) cblEcomm.get("items");
        String redirectUrl = items.get("url") + "?ORDERID=" + items.get("orderId") + "&SESSIONID=" + items.get("sessionId");

        model.addAttribute("redirectUrl", redirectUrl);

        return "redirect";
    }
}