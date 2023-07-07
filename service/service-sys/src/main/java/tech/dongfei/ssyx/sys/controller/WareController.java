package tech.dongfei.ssyx.sys.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.sys.Ware;
import tech.dongfei.ssyx.sys.service.WareService;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author dongfei
 * @since 2023-06-29
 */
@Api(tags = "仓库接口")
@RestController
@RequestMapping("/admin/sys/ware")
public class WareController {

    @Autowired
    private WareService wareService;

    //查询所有仓库列表
    @ApiOperation("查询所有仓库列表")
    @GetMapping("/findAllList")
    public Result findAllList() {
        List<Ware> wareList = wareService.list();
        return Result.ok(wareList);
    }

}

