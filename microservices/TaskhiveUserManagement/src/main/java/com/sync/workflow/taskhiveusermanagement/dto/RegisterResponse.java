package com.sync.workflow.taskhiveusermanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sync.workflow.taskhiveusermanagement.entity.User;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RegisterResponse {

    private String name;
    private String email;

    public RegisterResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
