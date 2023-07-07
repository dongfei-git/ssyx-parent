package tech.dongfei.ssyx.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.dongfei.ssyx.acl.service.RoleService;
import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.acl.Role;
import tech.dongfei.ssyx.vo.acl.RoleQueryVo;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //1.角色列表，条件分页查询
    @ApiOperation("角色条件分页查询")
    @GetMapping("/{current}/{limit}")
    public Result pageList(@PathVariable Long current,
                           @PathVariable Long limit,
                           RoleQueryVo roleQueryVo) {
        //创建page对象，传递当前页和每页记录数
        Page<Role> pageParam = new Page<>(current, limit);

        //调用service方法实现条件分页查询，返回分页对象
        IPage<Role> pageModel = roleService.selectRolePage(pageParam, roleQueryVo);
        return Result.ok(pageModel);
    }

    //2.根据id查询角色
    @ApiOperation("根据id查询角色")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    //3.添加角色
    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        return roleService.save(role)?Result.ok(null):Result.fail(null);
    }

    //4.修改角色
    @ApiOperation("修改角色")
    @PutMapping("/update")
    public Result update(@RequestBody Role role) {
        return roleService.updateById(role)?Result.ok(null):Result.fail(null);
    }


    //5.根据id删除角色
    @ApiOperation("根据id删除角色")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id){
        return roleService.removeById(id)?Result.ok(null):Result.fail(null);
    }

    //6.批量删除角色
    @ApiOperation("批量删除角色")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        return roleService.removeByIds(idList)?Result.ok(null):Result.fail(null);
    }

}
