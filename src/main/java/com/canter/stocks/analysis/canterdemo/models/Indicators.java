package com.canter.stocks.analysis.canterdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Indicators {

    @JsonProperty("quote")
    public List<Quote> quote;

    @JsonProperty("adjclose")
    public List<AdjClose> adjClose;

}