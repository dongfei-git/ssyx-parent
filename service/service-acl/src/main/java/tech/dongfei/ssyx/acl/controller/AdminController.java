package tech.dongfei.ssyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.dongfei.ssyx.acl.service.AdminService;
import tech.dongfei.ssyx.acl.service.RoleService;
import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.acl.Admin;
import tech.dongfei.ssyx.utils.MD5;
import tech.dongfei.ssyx.vo.acl.AdminQueryVo;

import java.util.List;
import java.util.Map;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/admin/acl/user")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    //1.用户列表
    @ApiOperation("用户列表")
    @GetMapping("/{current}/{limit}")
    public Result list(@PathVariable Long current,
                       @PathVariable Long limit,
                       AdminQueryVo adminQueryVo) {
        Page<Admin> pageParam = new Page<>(current, limit);
        IPage<Admin> pageModel = adminService.selectPageUser(pageParam, adminQueryVo);
        return Result.ok(pageModel);
    }

    //2.id查询用户
    @ApiOperation("根据id查询")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        return Result.ok(adminService.getById(id));
    }

    //3.添加用户
    @ApiOperation("添加用户")
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin) {
        //获取到输入的密码
        String password = admin.getPassword();
        //对输入的密码md5加密
        String passwordMd5 = MD5.encrypt(password);
        //设置到admin对象中
        admin.setPassword(passwordMd5);
        return adminService.save(admin) ? Result.ok(null) : Result.fail(null);
    }

    //4.修改用户
    @ApiOperation("修改用户")
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        return adminService.updateById(admin) ? Result.ok(null) : Result.fail(null);
    }

    //5.id删除用户
    @ApiOperation("根据id删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        return adminService.removeById(id) ? Result.ok(null) : Result.fail(null);
    }

    //6.批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        return adminService.removeByIds(idList) ? Result.ok(null) : Result.fail(null);
    }

    //7. 获取所有角色，根据用户id查询用户分配的角色列表，/toAssign/${adminId}
    @ApiOperation("获取所有角色")
    @GetMapping("/toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId) {
        Map<String, Object> map = roleService.getRoleByAdminId(adminId);
        return Result.ok(map);
    }

    //8.为用户分配角色, doAssign
    @ApiOperation("为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam Long adminId,
                           @RequestParam Long[] roleId){
        roleService.saveAdminRole(adminId, roleId);
        return Result.ok(null);
    }


}
