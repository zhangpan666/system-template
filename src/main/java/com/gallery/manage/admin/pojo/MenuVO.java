package com.gallery.manage.admin.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class MenuVO implements Serializable {

    private String icon;

    private String title;

    private String href;

    private boolean spread;

    private List<MenuVO> children;

}
