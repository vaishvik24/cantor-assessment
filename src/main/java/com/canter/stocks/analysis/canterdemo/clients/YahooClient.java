package com.canter.stocks.analysis.canterdemo.clients;

import com.canter.stocks.analysis.canterdemo.controllers.StocksController;
import com.canter.stocks.analysis.canterdemo.models.StockAnalysis;
import com.canter.stocks.analysis.canterdemo.repositories.StockAnalysisQuery;
import com.canter.stocks.analysis.canterdemo.utils.StockAnalysisJsonParser;
import com.canter.stocks.analysis.canterdemo.utils.StockAnalysisUtils;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

// Yahoo Client: Fetches stock analysis historical data for the given tracker and range
@Component
public class YahooClient {

    private final HttpClient httpClient;
    private final StockAnalysisUtils stockAnalysisUtils;
    private final StockAnalysisQuery stockAnalysisQuery;
    private final StockAnalysisJsonParser stockAnalysisJsonParser;

    // injecting dependencies
    @Autowired
    public YahooClient(HttpClient httpClient, StockAnalysisUtils stockAnalysisUtils, StockAnalysisQuery stockAnalysisQuery, StockAnalysisJsonParser stockAnalysisJsonParser) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        this.httpClient = httpClient;
        this.stockAnalysisUtils = stockAnalysisUtils;
        this.stockAnalysisQuery = stockAnalysisQuery;
        this.stockAnalysisJsonParser = stockAnalysisJsonParser;
    }


    Logger logger = LoggerFactory.getLogger(StocksController.class);

    // Return top 3 stock financial analysis data for a tracker
    private List<StockAnalysis> top3StockFinancialData(List<HistoricalQuote> historicalData) {
        List<StockAnalysis> stockAnalysisList = new LinkedList<>();

        if (historicalData.size() == 0) return stockAnalysisList;

        Pair<Double, Calendar> firstMax = new Pair<>(Double.MIN_VALUE, Calendar.getInstance());
        Pair<Double, Calendar> secondMax = new Pair<>(Double.MIN_VALUE, Calendar.getInstance());
        Pair<Double, Calendar> thirdMax = new Pair<>(Double.MIN_VALUE, Calendar.getInstance());

        HistoricalQuote prevHistData = historicalData.get(0);

        for (int i = 1; i < historicalData.size(); i++) {

            if (BigDecimal.ZERO.compareTo(prevHistData.getAdjClose()) == 0) continue;

            HistoricalQuote currHistData = historicalData.get(i);
            double percentageMove = stockAnalysisUtils.findPercentageMoves(prevHistData, currHistData);

            if (Math.abs(firstMax.getKey()) < Math.abs(percentageMove)) {
                thirdMax = secondMax;
                secondMax = firstMax;
                firstMax = new Pair<>(percentageMove, currHistData.getDate());
            } else if (Math.abs(secondMax.getKey()) < Math.abs(percentageMove)) {
                thirdMax = secondMax;
                secondMax = new Pair<>(percentageMove, currHistData.getDate());
            } else if (Math.abs(thirdMax.getKey()) < Math.abs(percentageMove)) {
                thirdMax = new Pair<>(percentageMove, currHistData.getDate());
            }

            prevHistData = currHistData;
        }

        stockAnalysisUtils.appendStockData(stockAnalysisList, firstMax);
        stockAnalysisUtils.appendStockData(stockAnalysisList, secondMax);
        stockAnalysisUtils.appendStockData(stockAnalysisList, thirdMax);

        return stockAnalysisList;
    }

    // return overall historical data of a tracker for a given range
    public List<HistoricalQuote> getStockHistoricalData(String symbol, String range) throws IOException {
        // get yahoo custom client url
        String yahooClientURL = stockAnalysisQuery.getYahooUrl(symbol, range);
        String stockHistoricalJsonContent = httpClient.getRequest(yahooClientURL);
        // parse historical json data to valid data class
        return stockAnalysisJsonParser.parseJsonStringData(symbol, stockHistoricalJsonContent);
    }

    // return final analysis report to service layer
    public List<StockAnalysis> getStockAnalysisReport(String symbol, String range) throws IOException {
        try {
            // retrieve overall historical data and return top 3 percentage moves along with date details for a tracker
            List<HistoricalQuote> historicalData = getStockHistoricalData(symbol, range);
            return top3StockFinancialData(historicalData);
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }
}
