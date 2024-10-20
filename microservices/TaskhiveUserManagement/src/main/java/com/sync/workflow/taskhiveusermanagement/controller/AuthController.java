package com.sync.workflow.taskhiveusermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sync.workflow.taskhiveusermanagement.dto.LoginRequest;
import com.sync.workflow.taskhiveusermanagement.dto.LoginResponse;
import com.sync.workflow.taskhiveusermanagement.dto.RegisterRequest;
import com.sync.workflow.taskhiveusermanagement.dto.RegisterResponse;
import com.sync.workflow.taskhiveusermanagement.entity.User;
import com.sync.workflow.taskhiveusermanagement.service.UserService;
import com.sync.workflow.taskhiveusermanagement.utils.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);
            
            jwtUtil.cacheToken(userDetails.getUsername(), jwt,jwtUtil.getTokenExpirationTime());

            return ResponseEntity.ok(new LoginResponse(jwt));
            
        } catch (BadCredentialsException e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try{
            User user=userService.registerUser(registerRequest);
            RegisterResponse registerResponse =new RegisterResponse(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks() {
       
            return ResponseEntity.status(HttpStatus.OK).body("Tasks api is working fine");
             
    }
    
    
}
