package tech.dongfei.ssyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.dongfei.ssyx.model.product.AttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.dongfei.ssyx.vo.product.AttrGroupQueryVo;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
public interface AttrGroupService extends IService<AttrGroup> {

    IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    List<AttrGroup> findAllListAttrGroup();
}
