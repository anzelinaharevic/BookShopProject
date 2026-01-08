package ie.atu.bookshopproject.FeignClient;

import ie.atu.bookshopproject.DTO.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "payment-service",
        url = "${payment.service.base-url}"
)

public interface paymentClient {
    @GetMapping("/api/payment/{id}")
    PaymentDTO getPaymentID(@PathVariable("id") Long paymentID);
}
