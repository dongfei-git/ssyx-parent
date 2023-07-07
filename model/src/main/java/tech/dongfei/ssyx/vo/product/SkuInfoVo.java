package tech.dongfei.ssyx.vo.product;

import tech.dongfei.ssyx.model.product.*;
import tech.dongfei.ssyx.model.product.SkuAttrValue;
import tech.dongfei.ssyx.model.product.SkuImage;
import tech.dongfei.ssyx.model.product.SkuInfo;
import tech.dongfei.ssyx.model.product.SkuPoster;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SkuInfoVo extends SkuInfo {

	@ApiModelProperty(value = "海报列表")
	private List<SkuPoster> skuPosterList;

	@ApiModelProperty(value = "属性值")
	private List<SkuAttrValue> skuAttrValueList;

	@ApiModelProperty(value = "图片")
	private List<SkuImage> skuImagesList;

}

