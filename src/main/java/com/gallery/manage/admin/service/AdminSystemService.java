package com.gallery.manage.admin.service;

import com.gallery.manage.admin.pojo.AdminPermissionVO;
import com.gallery.manage.common.model.Configuration;
import com.gallery.manage.common.model.OperationRecord;
import com.gallery.manage.common.model.Role;
import com.github.pagehelper.PageInfo;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;
import org.springframework.web.servlet.ModelAndView;

public interface AdminSystemService {

    PageInfo<Role> listRole(Page page, Role role);

    ModelAndView getPermissionDetailPage(Long id, ModelAndView request);

    boolean saveRole(Role role);

    boolean deleteRole(Long id);

    CommonResult savePermission(AdminPermissionVO permissions);

    Role getRoleDetail(Long id);

    void updateRole(Role role);

    PageInfo<Configuration> listConfiguration(Page page, Configuration configuration);

    Configuration getConfigurationById(Long id);

    CommonResult updateConfigurationById(Configuration configuration);

    CommonResult<Object> addConfiguration(Configuration configuration);

    boolean deleteConfigurationById(Long id);

    PageInfo<OperationRecord> listOperationRecord(Page page, OperationRecord operationRecord);
}
