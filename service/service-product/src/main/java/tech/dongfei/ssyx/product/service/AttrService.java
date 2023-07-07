package tech.dongfei.ssyx.product.service;

import tech.dongfei.ssyx.model.product.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
public interface AttrService extends IService<Attr> {

    List<Attr> getAttrListByGroupId(Long groupId);
}
