package com.zmg20200111.panda.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String username;
    private String userDescription;
    private String password;
    private List<UserRole> roles;
}
