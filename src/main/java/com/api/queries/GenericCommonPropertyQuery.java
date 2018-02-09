package com.api.queries;

import com.api.converter.JsonToCompanyProperty;
import com.entity.CompanyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GenericCommonPropertyQuery {
    @Autowired
    private JsonToCompanyProperty jsonToCompanyProperty;

    public List<CompanyProperty> query(String url) {
        ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class);

        return jsonToCompanyProperty.apply(response.getBody());
    }
}
