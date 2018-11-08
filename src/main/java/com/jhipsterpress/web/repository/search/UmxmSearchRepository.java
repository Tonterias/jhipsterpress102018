package com.jhipsterpress.web.repository.search;

import com.jhipsterpress.web.domain.Umxm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Umxm entity.
 */
public interface UmxmSearchRepository extends ElasticsearchRepository<Umxm, Long> {
}
