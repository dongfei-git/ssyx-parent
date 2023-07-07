package tech.dongfei.ssyx.acl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.dongfei.ssyx.acl.service.PermissionService;
import tech.dongfei.ssyx.acl.service.RolePermissionService;
import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.acl.Permission;
import tech.dongfei.ssyx.model.acl.RolePermission;

import java.util.List;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    //1.查询所有的菜单
    @ApiOperation("查询所有的菜单")
    @GetMapping
    public Result list() {
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }

    //2.添加菜单
    @ApiOperation("添加菜单")
    @PostMapping("/save")
    public Result save(@RequestBody Permission permission) {
        return permissionService.save(permission)?Result.ok(null):Result.fail(null);
    }

    //3.修改菜单
    @ApiOperation("修改菜单")
    @PutMapping("/update")
    public Result update(@RequestBody Permission permission) {
        return permissionService.updateById(permission)?Result.ok(null):Result.fail(null);
    }

    //4.删除菜单
    @ApiOperation("删除菜单")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }

    //5.查看角色的菜单权限列表
    @ApiOperation("查询角色权限列表")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        List<Permission> result = permissionService.getPermissionByRoleId(roleId);
        return Result.ok(result);
    }

    //6.给角色授权
    @ApiOperation("给某个角色授权")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam Long roleId, @RequestParam Long[] permissionId) {
        permissionService.saveRolePermission(roleId, permissionId);
        return Result.ok(null);
    }
}
