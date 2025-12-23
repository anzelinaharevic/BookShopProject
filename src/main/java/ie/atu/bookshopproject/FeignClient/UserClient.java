package ie.atu.bookshopproject.FeignClient;

import ie.atu.bookshopproject.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "login-service",
        url = "${login.service.base-url}"
)

public interface UserClient {
    @GetMapping("/api/user/{LoginId}")
    ResponseEntity<UserDTO> getUserID(@PathVariable Long loginId);
}
