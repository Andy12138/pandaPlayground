package com.zmg.panda.manage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Andy
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String username;
    private String userDescription;
    private String password;
    private List<UserRole> roles;
}
