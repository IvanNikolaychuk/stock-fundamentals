package com.api.once.converter;

import com.api.once.dto.CompanyDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static com.api.utils.JsonUtils.getAsStringSafely;

public class JsonToCompanyDto implements Function<String, List<CompanyDto>> {
    private static final SimpleDateFormat formatting = new SimpleDateFormat("yyyy-mm-dd");

    @Override
    public List<CompanyDto> apply(String json) {
        List<CompanyDto> companyDtos = new ArrayList<>();
        JsonArray companies = new JsonParser().parse(json).getAsJsonArray();

        for (int i = 0; i < companies.size(); i++) {
            JsonObject company = companies.get(i).getAsJsonObject();
            companyDtos.add(convert(company));
        }

        return companyDtos;
    }

    private CompanyDto convert(JsonObject jsonCompany) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.sector = getAsStringSafely(jsonCompany, "Sector");
        companyDto.industry = getAsStringSafely(jsonCompany, "Industry");
        companyDto.ticker = getAsStringSafely(jsonCompany, "Ticker");
        companyDto.exchange = getAsStringSafely(jsonCompany, "Exchange");
        companyDto.firstAdded = toDate(getAsStringSafely(jsonCompany, "First Added"));
        companyDto.lastUpdated = toDate(getAsStringSafely(jsonCompany, "Last Updated"));

        return companyDto;
    }


    private Date toDate(String dateString) {
        try {
            return dateString == null ? null : formatting.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
