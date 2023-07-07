package tech.dongfei.ssyx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.dongfei.ssyx.model.product.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import tech.dongfei.ssyx.vo.product.CategoryQueryVo;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author dongfei
 * @since 2023-06-30
 */
public interface CategoryService extends IService<Category> {

    IPage<Category> selectPageCategory(Page<Category> pageParam, CategoryQueryVo categoryQueryVo);
}
