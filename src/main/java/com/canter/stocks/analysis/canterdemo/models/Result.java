package com.canter.stocks.analysis.canterdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
public class Result {
    public ArrayList<HistoricalResult> result;
}
