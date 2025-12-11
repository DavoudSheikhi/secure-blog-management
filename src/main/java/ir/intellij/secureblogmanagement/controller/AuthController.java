package ir.intellij.secureblogmanagement.controller;

import ir.intellij.secureblogmanagement.dto.UserDtoRequest;
import ir.intellij.secureblogmanagement.model.Post;
import ir.intellij.secureblogmanagement.model.Role;
import ir.intellij.secureblogmanagement.model.User;
import ir.intellij.secureblogmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody UserDtoRequest userDtoRequest) {
        User newUser = User.builder()
                .username(userDtoRequest.username())
                .password(passwordEncoder.encode(userDtoRequest.password()))
                .role(Role.AUTHOR)
                .build();
        userService.save(newUser);

        return "User Added successfully";
    }


}
