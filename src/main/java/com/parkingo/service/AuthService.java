package com.parkingo.service;

import com.parkingo.dto.AuthResponse;
import com.parkingo.dto.LoginRequest;
import com.parkingo.dto.RegisterRequest;
import com.parkingo.exception.ApiException;
import com.parkingo.model.User;
import com.parkingo.repository.UserRepository;
import com.parkingo.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una cuenta con ese email");
        }

        User user = new User(
                req.fullName(),
                req.email(),
                passwordEncoder.encode(req.password()),
                req.role(),
                req.phone()
        );
        user = userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getFullName(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String token = jwtService.generateToken(user.getId(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getFullName(), user.getRole().name());
    }
}
