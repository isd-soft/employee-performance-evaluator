package com.isdintership.epe.entity;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum RoleEnum {
    @FieldNameConstants.Include
    ROLE_ADMIN,
    @FieldNameConstants.Include
    ROLE_USER,
    @FieldNameConstants.Include
    ROLE_SYSADMIN
}
