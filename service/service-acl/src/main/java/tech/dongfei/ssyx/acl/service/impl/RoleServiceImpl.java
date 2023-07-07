package tech.dongfei.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.dongfei.ssyx.acl.mapper.RoleMapper;
import tech.dongfei.ssyx.acl.service.AdminRoleService;
import tech.dongfei.ssyx.acl.service.RoleService;
import tech.dongfei.ssyx.model.acl.AdminRole;
import tech.dongfei.ssyx.model.acl.Role;
import tech.dongfei.ssyx.vo.acl.RoleQueryVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        //获取条件值
        String roleName = roleQueryVo.getRoleName();
        //创建mp条件对象
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        //判断条件值是否为空，不为空封装查询条件
        if (!StringUtils.isEmpty(roleName)) {
            wrapper.like(Role::getRoleName, roleName);
        }
        //调用方法实现分页查询, 返回分页对象
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        //查询所有角色
        List<Role> allRolesList = baseMapper.selectList(null);
        //根据id查询用户分配的角色列表
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        List<AdminRole> adminRoleList = adminRoleService.list(wrapper);
        List<Long> roleIdsList = adminRoleList.stream()
                .map(item -> item.getRoleId()).collect(Collectors.toList());
        List<Role> assignRoleList = new ArrayList<>();
        for (Role role : allRolesList) {
            if (roleIdsList.contains(role.getId())) {
                assignRoleList.add(role);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("allRolesList", allRolesList);
        result.put("assignRoles", assignRoleList);
        return result;
    }

    @Override
    public void saveAdminRole(Long adminId, Long[] roleIds) {
        //删除用户已经分配过的角色数据
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        adminRoleService.remove(wrapper);

        //重新分配
        List<AdminRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            list.add(adminRole);
        }
        adminRoleService.saveBatch(list);
    }
}
