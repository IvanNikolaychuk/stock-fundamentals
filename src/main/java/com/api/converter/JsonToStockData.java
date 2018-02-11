package com.api.converter;

import com.entity.StockData;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JsonToStockData {

    public StockData convert(String json) {
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();

        JsonObject financeData = extractFinanceData(root);
        List<Date> dates = extractDates(root);
        List<Double> closes = extract(financeData, "close");

        List<StockData> stockDataList = new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {
            StockData stockData = new StockData();
            stockData.setDate(dates.get(i));
            stockData.setClose(closes.get(i));

            stockDataList.add(stockData);
        }

        return stockDataList.get(0);
    }

    private static List<Date> extractDates(JsonObject root) {
        List<Date> dates = new ArrayList<>();

        JsonArray calendarsJson = root.get("chart").getAsJsonObject()
                .get("result").getAsJsonArray().get(0)
                .getAsJsonObject().get("timestamp").getAsJsonArray();

        for (int i = 0; i < calendarsJson.size(); i++) {
            dates.add(new Date(calendarsJson.get(i).getAsLong() * 1000));
        }

        return dates;
    }


    private static List<Double> extract(JsonObject financeData, String elemName) {
        List<Double> elements = new ArrayList<>();

        JsonArray jsonArray = financeData.get(elemName).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            if (jsonElement.getClass() == JsonNull.class) {
                elements.add(null);
            } else {
                elements.add(jsonElement.getAsDouble());
            }
        }

        return elements;
    }

    private static JsonObject extractFinanceData(JsonObject root) {
        return root.get("chart").getAsJsonObject()
                .get("result").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("indicators").getAsJsonObject()
                .get("quote").getAsJsonArray()
                .get(0).getAsJsonObject();
    }
}
