package com.canter.stocks.analysis.canterdemo.utils;

import com.canter.stocks.analysis.canterdemo.models.StockAnalysis;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

// Provide utilities method which can be used in overall package
@Component
public class StockAnalysisUtils {

    // add stock details pair to final stock analysis list with requested date format and decimal precision
    public void appendStockData(List<StockAnalysis> stockAnalysisList, Pair<Double, Calendar> maxDetails) {
        if (maxDetails.getKey() != Double.MIN_VALUE) {
            Date dateTime = maxDetails.getValue().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(dateTime);
            stockAnalysisList.add(new StockAnalysis(date, String.format("%.2f", maxDetails.getKey())));
        }
    }

    // find percentage difference b/w adjcloses
    public double findPercentageMoves(HistoricalQuote hq1, HistoricalQuote hq2) {
        return 100 * ((hq2.getAdjClose().doubleValue() - hq1.getAdjClose().doubleValue()) / hq1.getAdjClose().doubleValue());
    }

}
