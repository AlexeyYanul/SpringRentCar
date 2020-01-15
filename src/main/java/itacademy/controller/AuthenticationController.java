package itacademy.controller;

import itacademy.dto.UserRegistrationRequestDTO;
import itacademy.dto.request.AuthenticationRequestDTO;
import itacademy.dto.response.TokenResponseDTO;
import itacademy.model.User;
import itacademy.service.UserService;
import itacademy.service.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;


    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserService userService, TokenService tokenService, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signIn")
    public TokenResponseDTO authenticateUser(@RequestBody AuthenticationRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(requestDTO.getLogin(), requestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResponseDTO(tokenService.generate(authentication));
    }

    @PostMapping("/signUp")
    public User registerUser(@Valid @RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        final User user = new User();

        user.setLogin(userRegistrationRequestDTO.getLogin());
        user.setPassword(encoder.encode(userRegistrationRequestDTO.getPassword()));
        user.setRole(userRegistrationRequestDTO.getRole());
        user.setFirstName(userRegistrationRequestDTO.getFirstName());
        user.setLastName(userRegistrationRequestDTO.getLastName());
        user.setPhone(userRegistrationRequestDTO.getPhone());
        user.setEmail(userRegistrationRequestDTO.getEmail());
        user.setHomeAddress(userRegistrationRequestDTO.getHomeAddress());

        return userService.saveUser(user);
    }
}