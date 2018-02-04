package com.api.queries;

import com.api.converter.JsonToCompanyDto;
import com.api.dto.CompanyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.api.filter.CompanyFilter.ONLY_LISTED;
import static com.api.filter.CompanyFilter.filter;

public class ListedCompanies {
    private static final JsonToCompanyDto jsonToCompanyDto = new JsonToCompanyDto();

    private static final String URL = "http://www.sharadar.com/meta/tickers.json";

    public static List<CompanyDto> queryAllListed() {
        ResponseEntity<String> response = new RestTemplate().getForEntity(URL, String.class);
        final List<CompanyDto> allCompanies = jsonToCompanyDto.apply(response.getBody());
        return filter(allCompanies, ONLY_LISTED);
    }

}
