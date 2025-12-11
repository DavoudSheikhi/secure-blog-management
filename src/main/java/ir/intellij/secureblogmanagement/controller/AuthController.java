package ir.intellij.secureblogmanagement.controller;

import ir.intellij.secureblogmanagement.dto.UserDtoRequest;
import ir.intellij.secureblogmanagement.model.Role;
import ir.intellij.secureblogmanagement.model.User;
import ir.intellij.secureblogmanagement.service.UserService;
import ir.intellij.secureblogmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDtoRequest userDtoRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDtoRequest.username(), userDtoRequest.password()));
        String token = jwtUtil.generateToken(userDtoRequest.username());
        return ResponseEntity.ok(token);
    }



}
