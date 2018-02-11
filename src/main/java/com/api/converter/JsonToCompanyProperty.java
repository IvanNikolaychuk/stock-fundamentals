package com.api.converter;

import com.api.utils.YearFromDateExtractor;
import com.entity.CompanyProperty;
import com.entity.PropertyType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.api.Constants.DATA_KEY;
import static com.api.Constants.TICKER;

@Service
public class JsonToCompanyProperty implements Function<String, List<CompanyProperty>> {

    @Override
    public List<CompanyProperty> apply(String json) {
        JsonObject datatable = new JsonParser().parse(json).getAsJsonObject().get("datatable").getAsJsonObject();
        return getProperties(datatable);
    }

    private List<CompanyProperty> getProperties(JsonObject datatable) {
        JsonArray data = datatable.get("data").getAsJsonArray();

        List<CompanyProperty> companyProperties = new ArrayList<>();
        List<String> columns = getColumns(datatable);

        for (int i = 0; i < data.size(); i++) {
            JsonArray properties = data.get(i).getAsJsonArray();
            companyProperties.addAll(newProperties(properties, columns));
        }

        return companyProperties;
    }

    private List<CompanyProperty> newProperties(JsonArray properties, List<String> columns) {
        List<CompanyProperty> companyProperties = new ArrayList<>();

        final String yearString = properties.get(columns.indexOf(DATA_KEY)).getAsString();
        int year = YearFromDateExtractor.get(yearString);
        String ticker = properties.get(columns.indexOf(TICKER)).getAsString();

        for (int j = 0; j < properties.size(); j++) {
            final Double value = properties.get(j).isJsonNull() ? null : properties.get(j).getAsDouble();
            final PropertyType propertyType = PropertyType.find(columns.get(j));
            if (propertyType == PropertyType.TICKER || propertyType == PropertyType.DATA) continue;

            companyProperties.add(new CompanyProperty(ticker, year, propertyType, value));
        }

        return companyProperties;
    }

    private List<String> getColumns(JsonObject datatable) {
        List<String> columns = new ArrayList<>();
        JsonArray jsonColumns = datatable.get("columns").getAsJsonArray();

        for (int i = 0; i < jsonColumns.size(); i++) {
            String column = jsonColumns.get(i).getAsJsonObject().get("name").getAsString();
            columns.add(column);
        }

        return columns;
    }
}
