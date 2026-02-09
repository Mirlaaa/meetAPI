package br.com.amisahdev.meetAPI.controller;

import br.com.amisahdev.meetAPI.dto.request.UserRequest;
import br.com.amisahdev.meetAPI.dto.response.UserResponse;
import br.com.amisahdev.meetAPI.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user){
        return ResponseEntity.ok().body(userService.createUser(user));
    }

}
