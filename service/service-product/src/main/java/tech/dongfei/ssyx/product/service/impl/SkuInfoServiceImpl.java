package tech.dongfei.ssyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.dongfei.ssyx.model.product.SkuAttrValue;
import tech.dongfei.ssyx.model.product.SkuImage;
import tech.dongfei.ssyx.model.product.SkuInfo;
import tech.dongfei.ssyx.model.product.SkuPoster;
import tech.dongfei.ssyx.mq.constant.MqConst;
import tech.dongfei.ssyx.mq.service.RabbitMqService;
import tech.dongfei.ssyx.product.mapper.SkuInfoMapper;
import tech.dongfei.ssyx.product.service.SkuAttrValueService;
import tech.dongfei.ssyx.product.service.SkuImageService;
import tech.dongfei.ssyx.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.dongfei.ssyx.product.service.SkuPosterService;
import tech.dongfei.ssyx.vo.product.SkuInfoQueryVo;
import tech.dongfei.ssyx.vo.product.SkuInfoVo;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    //sku图片
    @Autowired
    private SkuImageService skuImageService;

    //sku平台属性
    @Autowired
    private SkuAttrValueService skuAttrValueService;

    //sku宣传海报
    @Autowired
    private SkuPosterService skuPosterService;

    @Autowired
    private RabbitMqService rabbitMqService;

    @Override
    public void saveSukInfo(SkuInfoVo skuInfoVo) {
        //1.添加sku基本信息
        //将SkuInfoVo放到SkuInfo中
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);

        //2.保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            skuPosterList.forEach(item -> item.setSkuId(skuInfo.getId()));
            skuPosterService.saveBatch(skuPosterList);
        }

        //3.保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            skuImagesList.forEach(item -> item.setSkuId(skuInfo.getId()));
            skuImageService.saveBatch(skuImagesList);
        }

        //4.保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            skuAttrValueList.forEach(item -> item.setSkuId(skuInfo.getId()));
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

    }

    @Override
    public IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        String keyword = skuInfoQueryVo.getKeyword();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        String skuType = skuInfoQueryVo.getSkuType();
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (!StringUtils.isEmpty(skuType)) {
            wrapper.like(SkuInfo::getSkuType, skuType);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public SkuInfoVo getSkuInfoById(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        //根据id查询sku基本信息
        SkuInfo skuInfo = baseMapper.selectById(id);
        //商品图片信息
        List<SkuImage> skuImageList = skuImageService.getSkuImageListBySkuId(id);
        //商品海报信息
        List<SkuPoster> skuPosterList = skuPosterService.getSkuPosterBySkuId(id);
        //商品属性信息
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.getSkuAttrValueBySkuId(id);
        //封装数据
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);

        return skuInfoVo;
    }

    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);

        Long id = skuInfoVo.getId();

        skuPosterService.remove(new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, id));
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            skuPosterList.forEach(item -> item.setSkuId(id));
            skuPosterService.saveBatch(skuPosterList);
        }

        skuImageService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, id));
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            skuImagesList.forEach(item -> item.setSkuId(id));
            skuImageService.saveBatch(skuImagesList);
        }

        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, id));
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            skuAttrValueList.forEach(item -> item.setSkuId(id));
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    @Override
    public void check(Long skuId, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public void publish(Long skuId, Integer status) {
        if (status == 1) {
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // 整合mq把数据同步到es里
            rabbitMqService.sendMessage(
                    MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_UPPER,
                    skuId);

        } else {
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // 整合mq把数据同步到es里
            rabbitMqService.sendMessage(
                    MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_LOWER,
                    skuId);
        }

    }

    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsNewPerson(status);
        baseMapper.updateById(skuInfoUp);
    }

    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {
        return baseMapper.selectBatchIds(skuIdList);
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName, keyword)
        );
    }

}
