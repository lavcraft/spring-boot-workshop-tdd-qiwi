package com.governance.embassy.port.input;

import com.governance.embassy.model.UserInfo;
import com.governance.embassy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ResolveUserIdController {
    private final UserService service;

    @PostMapping("/resolve-id")
    public ResponseEntity<UserIdResponse> userIdResponseResponseEntity(@RequestBody UserInfo userInfo) {
        String userId = service.resolveUserId(userInfo);

        return ResponseEntity.ok(
                UserIdResponse.builder()
                              .userId(userId)
                              .build()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<VisaStatusResponse> visaStatusResponseResponseEntity() {
        return ResponseEntity.badRequest()
                             .body(VisaStatusResponse.builder().status("invalid input").build());
    }
}
