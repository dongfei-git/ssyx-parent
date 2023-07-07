package tech.dongfei.ssyx.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.product.SkuInfo;
import tech.dongfei.ssyx.mq.constant.MqConst;
import tech.dongfei.ssyx.mq.service.RabbitMqService;
import tech.dongfei.ssyx.product.service.SkuInfoService;
import tech.dongfei.ssyx.vo.product.SkuInfoQueryVo;
import tech.dongfei.ssyx.vo.product.SkuInfoVo;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
@Api(tags = "sku信息接口")
@RestController
@RequestMapping("/admin/product/skuInfo")
@CrossOrigin
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private RabbitMqService rabbitMqService;

    @ApiOperation("sku列表")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = skuInfoService.selectPageSkuInfo(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("添加商品sku信息")
    @PostMapping("/save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSukInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation("根据id获取sku信息")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfoById(id);
        return Result.ok(skuInfoVo);
    }

    @ApiOperation("修改sku信息")
    @PutMapping("/update")
    public Result update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        rabbitMqService.sendMessage(
                MqConst.EXCHANGE_GOODS_DIRECT,
                MqConst.ROUTING_GOODS_LOWER,
                id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        for (Long id : idList) {
            rabbitMqService.sendMessage(
                    MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_LOWER,
                    id);
        }
        return Result.ok(null);
    }

    @ApiOperation("商品审核")
    @GetMapping("/check/{skuId}/{status}")
    public Result check(@PathVariable Long skuId,
                        @PathVariable Integer status) {
        skuInfoService.check(skuId, status);
        return Result.ok(null);
    }

    @ApiOperation("商品上架")
    @GetMapping("/publish/{skuId}/{status}")
    public Result publish(@PathVariable Long skuId,
                          @PathVariable Integer status) {
        skuInfoService.publish(skuId, status);
        return Result.ok(null);
    }

    @ApiOperation("新人专享")
    @GetMapping("isNewPerson/{skuId}/{status}")
    public Result isNewPerson(@PathVariable("skuId") Long skuId,
                              @PathVariable("status") Integer status) {
        skuInfoService.isNewPerson(skuId, status);
        return Result.ok(null);
    }
}

