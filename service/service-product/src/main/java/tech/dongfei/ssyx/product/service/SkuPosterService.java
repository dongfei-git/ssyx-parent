package tech.dongfei.ssyx.product.service;

import tech.dongfei.ssyx.model.product.SkuPoster;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> getSkuPosterBySkuId(Long id);
}
