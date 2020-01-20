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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The type Authentication controller.
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;

    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param userService           the user service
     * @param tokenService          the token service
     * @param encoder               the encoder
     * @param authenticationManager the authentication manager
     */
    public AuthenticationController(UserService userService, TokenService tokenService, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticate user token response dto.
     *
     * @param requestDTO the request dto
     * @return the token response dto
     */
    @PostMapping("/signIn")
    public TokenResponseDTO authenticateUser(@RequestBody AuthenticationRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(requestDTO.getLogin(), requestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResponseDTO(tokenService.generate(authentication));
    }

    /**
     * Register user user.
     *
     * @param userRegistrationRequestDTO the user registration request dto
     * @return the user
     */
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