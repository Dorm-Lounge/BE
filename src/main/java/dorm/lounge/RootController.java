package dorm.lounge;

import dorm.lounge.global.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.onSuccess("I'm healthy!");
    }

}
