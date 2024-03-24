package pl.edu.agh.repomanagement.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.payload.request.AuthRequest;
import pl.edu.agh.repomanagement.backend.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final RememberMeServices rememberMeServices;

    @Autowired
    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            SecurityContextRepository securityContextRepository,
            RememberMeServices rememberMeServices
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.securityContextRepository = securityContextRepository;
        this.rememberMeServices = rememberMeServices;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> userSignup(@RequestBody AuthRequest request) {
        if (userService.getUserByLogin(request.login()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = new User(
            request.login(),
            passwordEncoder.encode(request.password()));
        userService.saveUser(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(
            @RequestBody AuthRequest request,
            @RequestParam("remember-me") Boolean rememberMe,
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        Authentication authRequest =
            UsernamePasswordAuthenticationToken.unauthenticated(
                request.login(),
                request.password());
        Authentication authResponse = authenticationManager.authenticate(authRequest);

        if (authResponse.isAuthenticated()) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authResponse);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), req, res);

            if (rememberMe) {
                rememberMeServices.loginSuccess(req, res, authResponse);
            }

            return new ResponseEntity<>(request.login(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
