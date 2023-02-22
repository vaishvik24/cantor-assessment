package com.canter.stocks.analysis.canterdemo.repositories;

import com.canter.stocks.analysis.canterdemo.services.ApplicationProperties;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Provide Queries/URL to retrieve data from yahoo finance
@Component
public class StockAnalysisQuery {

    private final ApplicationProperties applicationProperties;

    // injecting dependencies
    @Autowired
    public StockAnalysisQuery(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    // provide yahoo url to get historical stock data
    public String getYahooUrl(String symbol, String range) {

        try {
            URIBuilder yahooFinanceCustomURL = new URIBuilder(applicationProperties.getYahooDomainUrl() + "/chart/" + symbol);
            // setting up the query parameters
            yahooFinanceCustomURL.addParameter("range", range);
            yahooFinanceCustomURL.addParameter("interval", applicationProperties.getInterval());
            yahooFinanceCustomURL.addParameter("indicators", applicationProperties.getIndicators());
            yahooFinanceCustomURL.addParameter("includeTimestamps", applicationProperties.isTimestampsIncluded());

            return yahooFinanceCustomURL.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
