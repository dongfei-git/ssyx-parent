package tech.dongfei.ssyx.product.service;

import tech.dongfei.ssyx.model.product.SkuImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> getSkuImageListBySkuId(Long id);
}
