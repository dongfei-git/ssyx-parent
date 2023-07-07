package tech.dongfei.ssyx.sys.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.sys.Region;
import tech.dongfei.ssyx.sys.service.RegionService;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author dongfei
 * @since 2023-06-29
 */
@Api(tags = "区域接口")
@RestController
@RequestMapping("/admin/sys/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    //根据区域的关键字查询区域列表信息, /findRegionByKeyword/${keyword}
    @ApiOperation("根据区域的关键字查询区域列表信息")
    @GetMapping("/findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable String keyword) {
        List<Region> regionList = regionService.getRegionByKeyword(keyword);
        return Result.ok(regionList);
    }


}

