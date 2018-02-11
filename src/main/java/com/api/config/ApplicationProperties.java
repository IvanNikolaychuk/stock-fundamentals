package com.api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class ApplicationProperties {
    @Value("${quandl.key}")
    private String apiKey;

    @Value("${create_companies_job}")
    private boolean createCompaniesJob;

    @Value("${query_stock_price_job}")
    private boolean queryStockPriceJob;

    @Value("${query_common_properties_job}")
    private boolean queryCommonPropertiesJob;

    @Value("${analyze_summary_job}")
    private boolean analyzeSummaryJob;
}
