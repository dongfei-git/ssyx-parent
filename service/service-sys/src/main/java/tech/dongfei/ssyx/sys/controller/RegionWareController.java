package tech.dongfei.ssyx.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.sys.RegionWare;
import tech.dongfei.ssyx.sys.service.RegionWareService;
import tech.dongfei.ssyx.vo.sys.RegionWareQueryVo;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author dongfei
 * @since 2023-06-29
 */
@Api(tags = "开通区域接口")
@RestController
@RequestMapping("/admin/sys/regionWare")
@CrossOrigin
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    //开通区域列表
    @ApiOperation("开通区域列表")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       RegionWareQueryVo regionWareQueryVo) {
        Page<RegionWare> pageParam = new Page<>(page, limit);
        IPage<RegionWare> pageModel = regionWareService.selectPageRegionWare(pageParam, regionWareQueryVo);
        return Result.ok(pageModel);
    }

    //添加开通区域
    @ApiOperation("添加开通区域")
    @PostMapping("/save")
    public Result addRegionWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }

    //删除开通区域
    @ApiOperation("删除开通区域")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        return regionWareService.removeById(id)?Result.ok(null):Result.fail(null);
    }

    //取消开通区域
    @ApiOperation("取消开通区域")
    @PostMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                               @PathVariable Integer status) {
        regionWareService.updateStatusById(id, status);
        return Result.ok(null);
    }
}

