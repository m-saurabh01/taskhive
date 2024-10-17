package com.sync.workflow.taskhiveusermanagement.service;


import com.sync.workflow.taskhiveusermanagement.dto.RegisterRequest;
import com.sync.workflow.taskhiveusermanagement.entity.User;

public interface UserService {

    public User registerUser(RegisterRequest registerRequest);
}
