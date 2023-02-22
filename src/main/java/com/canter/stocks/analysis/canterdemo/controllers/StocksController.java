package com.canter.stocks.analysis.canterdemo.controllers;

import com.canter.stocks.analysis.canterdemo.models.StockAnalysis;
import com.canter.stocks.analysis.canterdemo.services.StocksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Component
public class StocksController {

    private final StocksService stocksService;

    // injecting dependencies
    @Autowired
    public StocksController(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    Logger logger = LoggerFactory.getLogger(StocksController.class);


    // API to test whether the server is up or not
    @GetMapping("/sampleAPI/test")
    public String testAPI() {
        return "The server is up and running on port: 8080.....";
    }

    // API to get top 3 percentage moves for the given tracker for a specified range
    @GetMapping("/sampleAPI/analysis")
    public Map<String, List<StockAnalysis>> getStockAnalysisReport(@RequestParam String tickers, @RequestParam String range) {
        logger.info(tickers + " with range of " + range);
        String[] companies = tickers.split(",");
        return stocksService.getStockAnalysisReport(companies, range);
    }
}
