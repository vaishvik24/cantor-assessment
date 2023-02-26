package com.canter.stocks.analysis.canterdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class HistoricalResult {

    @JsonProperty("meta")
    public JsonNode meta;

    @JsonProperty("timestamp")
    public ArrayList<Long> timestamp;

    @JsonProperty("indicators")
    public Indicators indicators;
}