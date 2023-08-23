package com.gallery.manage.admin.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AdminPermissionVO implements Serializable {

    private Long roleId;

    private String permissionList;
}
