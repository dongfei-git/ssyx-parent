package tech.dongfei.ssyx.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.dongfei.ssyx.model.activity.CouponInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.dongfei.ssyx.vo.activity.CouponRuleVo;

import java.util.Map;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author dongfei
 * @since 2023-07-06
 */
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponInfo> selectPageCouponInfo(Long page, Long limit);

    CouponInfo getCouponInfo(Long id);

    Map<String, Object> findCouponRuleList(Long id);

    void saveCouponRule(CouponRuleVo couponRuleVo);
}
