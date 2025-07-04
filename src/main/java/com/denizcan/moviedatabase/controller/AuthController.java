package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.dto.AuthRequest;
import com.denizcan.moviedatabase.dto.AuthResponse;
import com.denizcan.moviedatabase.model.User;
import com.denizcan.moviedatabase.repository.UserRepository;
import com.denizcan.moviedatabase.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Kimlik Doğrulama", description = "Kullanıcı kayıt ve giriş işlemleri")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthController(AuthenticationManager authenticationManager, 
                         UserRepository userRepository, 
                         PasswordEncoder passwordEncoder, 
                         JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Kullanıcı kaydı", description = "Yeni kullanıcı kaydı oluşturur")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        // Kullanıcı adı kontrolü
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, "Bu kullanıcı adı zaten kullanılıyor", null, null));
        }
        
        // Email kontrolü
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, "Bu email adresi zaten kullanılıyor", null, null));
        }
        
        // Yeni kullanıcı oluştur
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(User.Role.USER); // Varsayılan rol
        
        userRepository.save(user);
        
        // JWT token oluştur
        String token = jwtUtil.generateToken(user);
        
        return ResponseEntity.ok(new AuthResponse(token, "Kullanıcı başarıyla kaydedildi", 
                                                 user.getUsername(), user.getRole().name()));
    }
    
    @PostMapping("/login")
    @Operation(summary = "Kullanıcı girişi", description = "Kullanıcı girişi yapar ve JWT token döner")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            
            User user = (User) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user);
            
            return ResponseEntity.ok(new AuthResponse(token, "Giriş başarılı", 
                                                     user.getUsername(), user.getRole().name()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, "Geçersiz kullanıcı adı veya şifre", null, null));
        }
    }
    
    @PostMapping("/register/admin")
    @Operation(summary = "Admin kaydı", description = "Yeni admin kullanıcısı kaydı oluşturur")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody AuthRequest request) {
        // Kullanıcı adı kontrolü
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, "Bu kullanıcı adı zaten kullanılıyor", null, null));
        }
        
        // Email kontrolü
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, "Bu email adresi zaten kullanılıyor", null, null));
        }
        
        // Yeni admin kullanıcısı oluştur
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(User.Role.ADMIN);
        
        userRepository.save(user);
        
        // JWT token oluştur
        String token = jwtUtil.generateToken(user);
        
        return ResponseEntity.ok(new AuthResponse(token, "Admin kullanıcısı başarıyla kaydedildi", 
                                                 user.getUsername(), user.getRole().name()));
    }
} 