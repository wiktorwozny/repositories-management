package pl.edu.agh.repomanagement.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.payload.request.AuthRequest;
import pl.edu.agh.repomanagement.backend.repositories.UserRepository;
import pl.edu.agh.repomanagement.backend.services.UserService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testUserSignupSuccessful() throws Exception {
        AuthRequest request = new AuthRequest("Test User", "password");
        when(userService.getUserByLogin(request.login())).thenReturn(null);

        mockMvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))

            .andExpect(status().isCreated());
    }

    @Test
    void testUserSignupFailed() throws Exception {
        AuthRequest request = new AuthRequest("Test User", "password");
        when(userService.getUserByLogin(request.login())).thenReturn(new User());

        mockMvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))

            .andExpect(status().isBadRequest());
    }

    @Test
    void testUserLoginSuccessful() throws Exception {
        AuthRequest request = new AuthRequest("Test User", "password");
        User savedUser = new User("Test User", passwordEncoder.encode("password"));
        when(userRepository.findByLogin(request.login())).thenReturn(Optional.of(savedUser));


        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .param("remember-me", "false"))

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.login").value("Test User"));
    }

    @Test
    void testUserLoginFailed() throws Exception {
        AuthRequest request = new AuthRequest("Test User", "password");
        when(userRepository.findByLogin(request.login())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .param("remember-me", "false"))

            .andExpect(status().isUnauthorized());
    }
}
