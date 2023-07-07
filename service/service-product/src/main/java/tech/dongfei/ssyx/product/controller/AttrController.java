package tech.dongfei.ssyx.product.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.product.Attr;
import tech.dongfei.ssyx.product.service.AttrService;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
@Api(tags = "商品属性接口")
@RestController
@RequestMapping("/admin/product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

    @ApiOperation("根据平台属性分组id查询")
    @GetMapping("/{groupId}")
    public Result list(@PathVariable Long groupId) {
        List<Attr> list = attrService.getAttrListByGroupId(groupId);
        return Result.ok(list);
    }


    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Attr attr = attrService.getById(id);
        return Result.ok(attr);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        attrService.removeByIds(idList);
        return Result.ok(null);
    }
}

