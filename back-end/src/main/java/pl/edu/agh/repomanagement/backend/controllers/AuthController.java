package pl.edu.agh.repomanagement.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.payload.request.AuthRequest;
import pl.edu.agh.repomanagement.backend.payload.response.LoginResponse;
import pl.edu.agh.repomanagement.backend.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final RememberMeServices rememberMeServices;

    @Autowired
    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository,
            RememberMeServices rememberMeServices
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.rememberMeServices = rememberMeServices;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> userSignup(@RequestBody AuthRequest request) {
        if (userService.getUserByLogin(request.login()) != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        User user = new User(
            request.login(),
            request.password());
        userService.saveUser(user);

        return new ResponseEntity<>("Successfully signed up", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(
            @RequestBody AuthRequest request,
            @RequestParam("remember-me") Boolean rememberMe,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        try {
            Authentication authRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            request.login(),
                            request.password());

            Authentication authResponse = authenticationManager.authenticate(authRequest); // throws AuthenticationException if authentication fails

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authResponse);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), req, res);

            if (rememberMe) {
                rememberMeServices.loginSuccess(req, res, authResponse);
            }

            return new ResponseEntity<>(new LoginResponse(request.login()), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("User does not exist", HttpStatus.UNAUTHORIZED);
        }
    }

    // logout implemented in backend/security/WebSecurityConfig.java as Spring Docs suggest
}
