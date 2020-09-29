package com.example.springmanager.controller;

import com.example.springmanager.dao.domain.UserPrincipal;
import com.example.springmanager.jwt.AuthenticationRequest;
import com.example.springmanager.jwt.AuthenticationResponse;
import com.example.springmanager.jwt.JwtUtil;
import com.example.springmanager.service.UserPrincipalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.99.100:4200"})
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserPrincipalService userPrincipalService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, UserPrincipalService userPrincipalService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userPrincipalService = userPrincipalService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new Exception("Incorrect username or password !");
        }
        final UserDetails userDetails = userPrincipalService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse("Bearer " + jwt));

    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        final String token = authToken.substring(7);
        String username = jwtUtil.extractUsername(token);

        UserPrincipal userPrincipal = (UserPrincipal) userPrincipalService.loadUserByUsername(username);

        if (jwtUtil.canTokenRefreshed(token) && userPrincipal != null) {
            String newToken = jwtUtil.refreshToken(token);
            return ResponseEntity.ok(new AuthenticationResponse(newToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
