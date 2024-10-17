package com.sync.workflow.taskhiveusermanagement.serviceImpl;

import com.sync.workflow.taskhiveusermanagement.dto.RegisterRequest;
import com.sync.workflow.taskhiveusermanagement.entity.User;
import com.sync.workflow.taskhiveusermanagement.repository.UserRepository;
import com.sync.workflow.taskhiveusermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(RegisterRequest registerRequest) {

        Optional<User> user = userRepository.findByEmail(registerRequest.getEmail());

        if (user.isPresent()) {
            throw new IllegalArgumentException(registerRequest.getEmail() + " is already registered");
        }

        User userEntity = new User();
        userEntity.setName(registerRequest.getName());
        userEntity.setEmail(registerRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        System.out.println(registerRequest.getPassword()+" "+passwordEncoder.encode(registerRequest.getPassword()));
        userEntity.setRole("VIEWER");

        return userRepository.save(userEntity);
    }
}
