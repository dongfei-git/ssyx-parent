package tech.dongfei.ssyx.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.dongfei.ssyx.model.acl.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllPermission();

    void removeChildById(Long id);

    List<Permission> getPermissionByRoleId(Long roleId);

    void saveRolePermission(Long roleId, Long[] permissionId);
}
