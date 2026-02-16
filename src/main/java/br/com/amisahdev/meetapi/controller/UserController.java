package br.com.amisahdev.meetapi.controller;

import br.com.amisahdev.meetapi.dto.response.UserResponse;
import br.com.amisahdev.meetapi.security.AuthenticatedUser;
import br.com.amisahdev.meetapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {

    private final UserService userService;

    @GetMapping("me/")
    private ResponseEntity<UserResponse> getMe(final @AuthenticationPrincipal AuthenticatedUser userPrincipal) {
        return ResponseEntity.ok().body(userService.findById(userPrincipal.id()));
    }

    @DeleteMapping
    public ResponseEntity<UserResponse> deleteUser(final @AuthenticationPrincipal AuthenticatedUser userPrincipal) {
        return ResponseEntity.ok().body(userService.delete(userPrincipal.id()));
    }

}
