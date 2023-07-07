package tech.dongfei.ssyx.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tech.dongfei.ssyx.activity.mapper.ActivityInfoMapper;
import tech.dongfei.ssyx.activity.mapper.ActivityRuleMapper;
import tech.dongfei.ssyx.activity.mapper.ActivitySkuMapper;
import tech.dongfei.ssyx.activity.service.ActivityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.dongfei.ssyx.client.product.ProductFeignClient;
import tech.dongfei.ssyx.model.activity.ActivityInfo;
import tech.dongfei.ssyx.model.activity.ActivityRule;
import tech.dongfei.ssyx.model.activity.ActivitySku;
import tech.dongfei.ssyx.model.product.SkuInfo;
import tech.dongfei.ssyx.vo.activity.ActivityRuleVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author dongfei
 * @since 2023-07-06
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;

    @Autowired
    private ActivitySkuMapper activitySkuMapper;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        Page<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam, null);
        List<ActivityInfo> activityInfoList = activityInfoPage.getRecords();
        activityInfoList.stream().forEach(item ->
                item.setActivityTypeString(item.getActivityType().getComment())
        );
        return activityInfoPage;
    }

    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        HashMap<String, Object> result = new HashMap<>();

        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(
                new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, id));
        result.put("activityRuleList", activityRuleList);

        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(
                new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id)
        );
        List<Long> skuIdList = activitySkuList.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        result.put("skuInfoList", skuInfoList);
        return result;

    }

    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        Long activityId = activityRuleVo.getActivityId();
        activityRuleMapper.delete(
                new LambdaQueryWrapper<ActivityRule>()
                        .eq(ActivityRule::getActivityId, activityId)
        );
        activitySkuMapper.delete(
                new LambdaQueryWrapper<ActivitySku>()
                        .eq(ActivitySku::getActivityId, activityId)
        );

        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        ActivityInfo activityInfo = baseMapper.selectById(activityId);
        activityRuleList.forEach(item -> {
            item.setActivityId(activityId);
            item.setActivityType(activityInfo.getActivityType());
            activityRuleMapper.insert(item);
        });

        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        activitySkuList.forEach(item -> {
            item.setActivityId(activityId);
            activitySkuMapper.insert(item);
        });
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoByKeyword(keyword);
        if (CollectionUtils.isEmpty(skuInfoList)) {
            return skuInfoList;
        }

        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());
        List<Long> existSkuIdList = baseMapper.selectSkuIdListExist(skuIdList);

        ArrayList<SkuInfo> findSkuList = new ArrayList<>();
        for (SkuInfo skuInfo : skuInfoList) {
            if (!existSkuIdList.contains(skuInfo.getId())) {
                findSkuList.add(skuInfo);
            }
        }

        return findSkuList;
    }
}
