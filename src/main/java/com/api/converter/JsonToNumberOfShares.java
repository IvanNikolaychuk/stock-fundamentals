package com.api.converter;

import com.entity.CompanyProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Function;

import static com.api.utils.YearFromDateExtractor.get;

@Service
public class JsonToNumberOfShares implements Function<String, CompanyProperty> {

    @Override
    public CompanyProperty apply(String json) {
        CompanyProperty companyProperty = new CompanyProperty();

        JsonArray dateToNumberOfShares = new JsonParser().parse(json).getAsJsonObject()
                .get("dataset").getAsJsonObject().get("data").getAsJsonArray();

        for (int i = 0; i < dateToNumberOfShares.size(); i++) {
            JsonArray dateToShareNumber = dateToNumberOfShares.get(i).getAsJsonArray();
            final int year = get(dateToShareNumber.get(0).getAsString());
            final BigDecimal shares = dateToShareNumber.get(1).getAsBigDecimal();
            companyProperty.add(year, shares);
        }

        return companyProperty;
    }
}
