package com.canter.stocks.analysis.canterdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AdjClose {
    @JsonProperty("adjclose")
    public ArrayList<BigDecimal> adjClose;
}

