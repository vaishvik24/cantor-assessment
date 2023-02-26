package com.canter.stocks.analysis.canterdemo.utils;

import com.canter.stocks.analysis.canterdemo.models.Chart;
import com.canter.stocks.analysis.canterdemo.models.HistoricalResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Component
public class StockAnalysisJsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // parse json data to historical data quote using jackson mapper for a tracker
    public List<HistoricalQuote> parseJsonStringData(String symbol, String json) throws IOException {
        Chart chart = objectMapper.readValue(json, Chart.class);
        HistoricalResult historicalResult = chart.chart.result.get(0);
        List<HistoricalQuote> historicalQuoteData = new LinkedList<>();

        for (int i = 0; i < historicalResult.timestamp.size(); i++) {
            long timestamp = historicalResult.timestamp.get(i);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp * 1000);
            BigDecimal adjClose = historicalResult.indicators.adjClose.get(0).adjClose.get(i);
            long volume = historicalResult.indicators.quote.get(0).volume.get(i);
            BigDecimal open = historicalResult.indicators.quote.get(0).open.get(i);
            BigDecimal high = historicalResult.indicators.quote.get(0).high.get(i);
            BigDecimal low = historicalResult.indicators.quote.get(0).low.get(i);
            BigDecimal close = historicalResult.indicators.quote.get(0).close.get(i);

            HistoricalQuote historicalQuote = new HistoricalQuote(symbol, calendar, open, low, high, close, adjClose, volume);
            historicalQuoteData.add(historicalQuote);
        }

        return historicalQuoteData;
    }

    // parse json data to historical data quote using jackson mapper for a tracker
//    public List<HistoricalQuote> parseData(String symbol, String json) throws IOException {
//        JsonNode resultNode = objectMapper.readTree(json).get("chart").get("result").get(0);
//        JsonNode timestamps = resultNode.get("timestamp");
//        JsonNode indicators = resultNode.get("indicators");
//        JsonNode quotes = indicators.get("quote").get(0);
//        JsonNode closes = quotes.get("close");
//        JsonNode volumes = quotes.get("volume");
//        JsonNode opens = quotes.get("open");
//        JsonNode highs = quotes.get("high");
//        JsonNode lows = quotes.get("low");
//        JsonNode adjCloses = indicators.get("adjclose").get(0).get("adjclose");
//
//        List<HistoricalQuote> historicalQuoteData = new LinkedList<>();
//        for (int i = 0; i < timestamps.size(); i++) {
//            long timestamp = timestamps.get(i).asLong();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(timestamp * 1000);
//            BigDecimal adjClose = adjCloses.get(i).decimalValue();
//            long volume = volumes.get(i).asLong();
//            BigDecimal open = opens.get(i).decimalValue();
//            BigDecimal high = highs.get(i).decimalValue();
//            BigDecimal low = lows.get(i).decimalValue();
//            BigDecimal close = closes.get(i).decimalValue();
//
//            HistoricalQuote historicalQuote = new HistoricalQuote(symbol, calendar, open, low, high, close, adjClose, volume);
//            historicalQuoteData.add(historicalQuote);
//        }
//
//        return historicalQuoteData;
//    }

}
