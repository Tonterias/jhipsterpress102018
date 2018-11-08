package com.jhipsterpress.web.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of UmxmSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UmxmSearchRepositoryMockConfiguration {

    @MockBean
    private UmxmSearchRepository mockUmxmSearchRepository;

}
