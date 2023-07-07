package tech.dongfei.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.dongfei.ssyx.acl.mapper.PermissionMapper;
import tech.dongfei.ssyx.acl.service.PermissionService;
import tech.dongfei.ssyx.acl.service.RolePermissionService;
import tech.dongfei.ssyx.acl.utils.PermissionHelper;
import tech.dongfei.ssyx.model.acl.Permission;
import tech.dongfei.ssyx.model.acl.RolePermission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    //查询所有菜单
    @Override
    public List<Permission> queryAllPermission() {
        List<Permission> allPermissionList = baseMapper.selectList(null);
        return PermissionHelper.buildPermission(allPermissionList);
    }

    //递归删除菜单
    @Override
    public void removeChildById(Long id) {
        List<Long> idsList = new ArrayList<>();
        idsList.add(id);
        this.getAllPermissionIds(id, idsList);
        baseMapper.deleteBatchIds(idsList);
    }

    private void getAllPermissionIds(Long id, List<Long> idsList) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid, id);
        List<Permission> childList = baseMapper.selectList(wrapper);
        //递归查询是否还有子菜单
        childList.stream().forEach(item -> {
            idsList.add(item.getId());
            this.getAllPermissionIds(item.getId(), idsList);
        });
    }

    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(null);
        List<Permission> buildPermissionList = PermissionHelper.buildPermission(allPermissionList);

        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        List<RolePermission> rolePermissionList = rolePermissionService.list(wrapper);
        List<Long> selectPermissionIdsList = rolePermissionList.stream().map(item -> item.getPermissionId())
                .collect(Collectors.toList());
        this.setPermissionIsSelect(buildPermissionList, selectPermissionIdsList);
        return buildPermissionList;
    }

    private void setPermissionIsSelect(List<Permission> buildPermissionList,
                                       List<Long> selectPermissionIdsList){
        for (Permission permission : buildPermissionList) {
            if (selectPermissionIdsList.contains(permission.getId())) {
                permission.setSelect(true);
            }
            if (permission.getChildren() != null) {
                this.setPermissionIsSelect(permission.getChildren(), selectPermissionIdsList);
            }
        }
    }

    @Override
    public void saveRolePermission(Long roleId, Long[] permissionId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionService.remove(wrapper);

        List<RolePermission> list = new ArrayList<>();
        for (Long pid : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(pid);
            list.add(rolePermission);
        }
        rolePermissionService.saveBatch(list);
    }
}
