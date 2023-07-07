package tech.dongfei.ssyx.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tech.dongfei.ssyx.model.search.SkuEs;

public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {
}
