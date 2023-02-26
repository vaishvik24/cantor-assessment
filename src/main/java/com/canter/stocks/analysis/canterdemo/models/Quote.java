package com.canter.stocks.analysis.canterdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Quote {
    @JsonProperty("high")
    public ArrayList<BigDecimal> high;
    @JsonProperty("low")
    public ArrayList<BigDecimal> low;
    @JsonProperty("open")
    public ArrayList<BigDecimal> open;
    @JsonProperty("close")
    public ArrayList<BigDecimal> close;
    @JsonProperty("volume")
    public ArrayList<Long> volume;
}