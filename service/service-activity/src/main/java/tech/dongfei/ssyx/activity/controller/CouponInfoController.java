package tech.dongfei.ssyx.activity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tech.dongfei.ssyx.activity.service.CouponInfoService;
import tech.dongfei.ssyx.common.result.Result;
import tech.dongfei.ssyx.model.activity.CouponInfo;
import tech.dongfei.ssyx.vo.activity.CouponRuleVo;

import java.util.Map;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author dongfei
 * @since 2023-07-06
 */
@Api(tags = "优惠卷接口")
@RestController
@RequestMapping("/admin/activity/couponInfo")
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit) {
        IPage<CouponInfo> pageModel =
                couponInfoService.selectPageCouponInfo(page, limit);
        return Result.ok(pageModel);
    }

    @PostMapping("/save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        return couponInfoService.save(couponInfo)?Result.ok(null):Result.fail(null);
    }

    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        CouponInfo couponInfo =  couponInfoService.getCouponInfo(id);
        return Result.ok(couponInfo);
    }

    @GetMapping("/findCouponRuleList/{id}")
    public Result findCouponRuleList(@PathVariable Long id) {
        Map<String, Object> map =
                couponInfoService.findCouponRuleList(id);
        return Result.ok(map);
    }

    @PostMapping("/saveCouponRule")
    public Result saveCouponRule(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok(null);
    }
}

