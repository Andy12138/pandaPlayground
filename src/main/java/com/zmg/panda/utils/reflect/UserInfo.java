package com.zmg.panda.utils.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserInfo {
    private String account;
    private String userName;
    private String gender;
    private String birthDay;
}
