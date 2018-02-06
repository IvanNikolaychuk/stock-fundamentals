package com.api.converter;

import com.entity.Company;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.api.utils.JsonUtils.getAsStringSafely;

@Component
public class JsonToCompany implements Function<String, List<Company>> {
    @Override
    public List<Company> apply(String json) {
        List<Company> companyList = new ArrayList<>();
        JsonArray companies = new JsonParser().parse(json).getAsJsonArray();

        for (int i = 0; i < companies.size(); i++) {
            JsonObject company = companies.get(i).getAsJsonObject();
            companyList.add(convert(company));
        }

        return companyList;
    }

    private Company convert(JsonObject jsonCompany) {
        Company company = new Company();
        company.setSector(getAsStringSafely(jsonCompany, "Sector"));
        company.setIndustry(getAsStringSafely(jsonCompany, "Industry"));
        company.setTicker(getAsStringSafely(jsonCompany, "Ticker"));
        company.setExchange(getAsStringSafely(jsonCompany, "Exchange"));
        company.setFirstAdded(StringToDate.convert(getAsStringSafely(jsonCompany, "First Added")));
        company.setLastUpdated(StringToDate.convert(getAsStringSafely(jsonCompany, "Last Updated")));

        return company;
    }
}
