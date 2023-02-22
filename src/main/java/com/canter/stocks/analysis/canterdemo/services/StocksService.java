package com.canter.stocks.analysis.canterdemo.services;

import com.canter.stocks.analysis.canterdemo.clients.YahooClient;
import com.canter.stocks.analysis.canterdemo.controllers.StocksController;
import com.canter.stocks.analysis.canterdemo.models.StockAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.*;

@Component
public class StocksService {

    private YahooClient yahooClient;

    // injecting dependencies
    @Autowired
    public StocksService(YahooClient yahooClient) {
        this.yahooClient = yahooClient;
    }

    Logger logger = LoggerFactory.getLogger(StocksController.class);

    // return map of top 3 stock analysis percentage moves for each tracker
    public Map<String, List<StockAnalysis>> getStockAnalysisReport(String[] symbols, String range) {

        Map<String, List<StockAnalysis>> stockAnalysisReport = new HashMap<>();
        try {
            // for each given tracker, get report from service layer
            for (String symbol : symbols) {
                logger.info("Processing symbol: " + symbol + " with range = " + range);
                List<StockAnalysis> stockAnalysisRep = yahooClient.getStockAnalysisReport(symbol, range);
                stockAnalysisReport.put(symbol, stockAnalysisRep);
            }
            return stockAnalysisReport;
        } catch (IOException e) {
            logger.error("Failed to process stock analysis data. ERROR: " + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }
}
