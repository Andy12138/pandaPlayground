package com.zmg.panda.common.constants;

import lombok.Getter;

/**
 * @author Panda
 */

public enum RoleEnum {
    // 管理员
    ADMIN(1, "管理员"),
    // 普通人
    CUSTOMER(2, "游客")
    ;

    private Integer roleId;
    @Getter
    private String roleName;

    RoleEnum(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
