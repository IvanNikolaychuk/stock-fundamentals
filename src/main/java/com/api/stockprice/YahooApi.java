package com.api.stockprice;

import com.api.converter.JsonToStockData;
import com.entity.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class YahooApi {
    @Autowired
    private JsonToStockData jsonToStockData;

    private static final String BASE_URL = "https://query2.finance.yahoo.com/v8/finance/chart/{0}?formatted=true" +
            "&crumb=BEbgjvn1CG8&lang=en-US&region=US&interval=1d&events=div%7Csplit&corsDomain=finance.yahoo.com";

    public Optional<StockData> queryMostResent(String ticker) {
        try {
            String url = MessageFormat.format(BASE_URL, ticker);
            System.out.println("URL: " + url);
            String json = new RestTemplate().getForEntity(url, String.class).getBody();
            StockData stockData = jsonToStockData.convert(json);
            stockData.setTicker(ticker);

            return Optional.of(stockData);
        } catch (Throwable throwable) {
            return Optional.empty();
        }
    }
}
