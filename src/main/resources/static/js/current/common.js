$(function () {
    $("button").click(function (event) {
        var id = event.target.id
        if (id === 'getRoleAddPage') {
            CommonUtil.open('角色-添加', ['850px', '670px'], '/gallery/manage/admin/system/role/getAddPage')
        } else if (id === 'getSysUserAddPage') {
            CommonUtil.open('系统用户-添加', ['850px', '670px'], '/gallery/manage/admin/usergetAddPage');
        } else if (id === 'getConfigurationAddPage') {
            CommonUtil.open('常量配置-添加', ['850px', '650px'], '/gallery/manage/admin/system/configuration/getAddPage')
        }

    });

    $("img").click(function (event) {
        var target = event.target;
        AlertUtil.pictureView(target.src, [target.naturalWidth * 0.5 + "px", target.naturalHeight * 0.5 + "px"]);
    });
})

function updateById(id, type, args) {
    if (type === 'role') {
        CommonUtil.open('角色-编辑', ['850px', '600px'], '/gallery/manage/admin/system/role/getUpdatePage/' + id)
    } else if (type === 'permission') {
        CommonUtil.open('角色-权限设置', ['1000px', '700px'], '/gallery/manage/admin/system/role/getPermissionDetailPage/' + id);
    } else if (type === 'sysUser') {
        CommonUtil.open('系统用户-编辑', ['850px', '600px'], '/gallery/manage/admin/sysUser/getUpdatePage/' + id);
    } else if (type === 'sysUserAuth') {
        CommonUtil.open('系统用户-授权', ['850px', '600px'], '/gallery/manage/admin/sysUser/getAuthPage/' + id);
    } else if (type === 'sysUserRole') {
        CommonUtil.open('系统用户角色-编辑', ['850px', '600px'], '/gallery/manage/admin/sysUser/role/getUpdatePage/' + id);
    } else if (type === 'configuration') {
        CommonUtil.open('常量配置-编辑', ['850px', '650px'], '/gallery/manage/admin/system/configuration/getUpdatePage/' + id)
    } else {
        AlertUtil.error("暂无该选项");
    }
}


function deleteById(id, type) {
    var url;
    if (type === 'role') {
        url = "/gallery/manage/admin/system/role/delete/" + id;
    } else if (type === 'bg-user') {
        url = "/gallery/manage/admin/sysUser/delete/" + id;
    } else if (type === 'configuration') {
        url = "/gallery/manage/admin/system/configuration/delete/" + id;
    } else {
        AlertUtil.error("暂无该选项");
    }
    AlertUtil.confirm('你确定要删除吗？', function () {
        RequestUtil.post(url, null, function (result) {
            if (result.code == 0) {
                AlertUtil.success("删除成功", 1000, function () {
                    window.location.reload();
                })
            } else {
                AlertUtil.error("删除失败");
            }
        }, "json")
    });
}


function batchDelete(type) {
    let url;
    if (type === 'info') {
        url = "/gallerynew/admin/info/deleteBatch";
    }
    var ids = [];
    $("input[name='check']").each(function () {
        if ($(this).prop('checked')) {
            var id = $(this).val();
            if (id != null && id != '') {
                ids.push(id);
            }
        }
    })
    if (ids.length == 0) {
        AlertUtil.error("请选择要操作的数据");
        return;
    }
    AlertUtil.confirm('确定要删除吗？', function () {
        CommonUtil.ajaxRequestTraditional({ids: ids}, url, function (result) {
            if (result.subCode === 10000) {
                AlertUtil.success("操作成功", 1000);
                $("#entity_search_form").submit();
            } else {
                AlertUtil.error(result.subMsg, 1000);
            }
        })
    })
}


function roleSaveOrUpdate() {
    var id = $("#id").val();
    if (id == null || id == undefined || id == "") {
        CommonUtil.ajaxRequest($("#form").serialize(), "/gallery/manage/admin/system/role/add", function (result) {
            if (result.code === 0) {
                AlertUtil.success(result.message, 2000, function () {
                    CommonUtil.close();
                })
            } else {
                AlertUtil.error(result.message);
            }
        })
    } else {
        CommonUtil.ajaxRequest($("#form").serialize(), "/gallery/manage/admin/system/role/update", function (result) {
            if (result.code === 0) {
                AlertUtil.success(result.message, 2000, function () {
                    CommonUtil.close();
                })
            } else {
                AlertUtil.error(result.message);
            }
        })
    }

    return false;
}


function sysUserSaveOrUpdate() {
    var id = $("#id").val();
    if (id != null && id != undefined && id != '') {
        CommonUtil.ajaxRequest($("#form").serialize(), "/gallery/manage/admin/sysUser/update", function (result) {
            if (result.code === 0) {
                AlertUtil.success(result.message, 1000, function () {
                    CommonUtil.close();
                })
            } else {
                AlertUtil.error(result.message);
            }
        })
    } else {
        CommonUtil.ajaxRequest($("#form").serialize(), "/gallery/manage/admin/sysUser/save", function (result) {
            if (result.code === 0) {
                AlertUtil.success(result.message, 1000, function () {
                    CommonUtil.close();
                })
            } else {
                AlertUtil.error(result.message);
            }
        })
    }
    return false;
}


function userAuth() {
    CommonUtil.ajaxRequest($("#form").serialize(), "/gallery/manage/admin/sysUser/auth", function (result) {
        if (result.code === 0) {
            AlertUtil.success(result.message, 1000, function () {
                CommonUtil.close();
            })
        } else {
            AlertUtil.error(result.message);
        }
    })
    return false;
}


function configurationSaveOrUpdate() {
    var id = $("input[name=id]").val();
    if (id == null || id == undefined || id == "") {
        CommonUtil.ajaxRequest($("#form2").serialize(), "/gallery/manage/admin/system/configuration/add", function (result) {
            if (result.code === 0) {
                AlertUtil.success(result.message, 1000, function () {
                    CommonUtil.close();
                })
            } else {
                AlertUtil.error(result.message, 1000);
            }
        })
    } else {
        CommonUtil.ajaxRequest($("#form2").serialize(), "/gallery/manage/admin/system/configuration/update", function (result) {
            if (result.code === 0) {
                AlertUtil.success(result.message, 1000, function () {
                    CommonUtil.close();
                })
            } else {
                AlertUtil.error(result.message, 1000);
            }
        })
    }
    return false;
}

