package tech.dongfei.ssyx.product.service;

import tech.dongfei.ssyx.model.product.SkuAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    List<SkuAttrValue> getSkuAttrValueBySkuId(Long id);
}
