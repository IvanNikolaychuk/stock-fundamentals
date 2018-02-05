package com.api.queries;

import com.api.config.ApplicationProperties;
import com.api.converter.JsonToNumberOfShares;
import com.entity.CompanyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Service
public class NumberOfShares {
    private static final String URL_TEMPLATE
            = "https://www.quandl.com/api/v3/datasets/SF1/{0}_SHARESBAS.json?collapse=annual&api_key={1}";

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private JsonToNumberOfShares jsonToNumberOfShares;

    public CompanyProperty query(String ticker) {
        final String url = MessageFormat.format(URL_TEMPLATE, ticker, properties.getApiKey());
        ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class);

        return jsonToNumberOfShares.apply(response.getBody());
    }
}
