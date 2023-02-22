package com.canter.stocks.analysis.canterdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// class to retrieve values from application properties
@Component
public class ApplicationProperties {

    private Environment env;

    @Autowired
    public ApplicationProperties(Environment env) {
        this.env = env;
    }

    public String getYahooDomainUrl() {
        return env.getProperty("yahoo.domain.url");
    }

    public String getInterval() {
        return env.getProperty("yahoo.interval");
    }

    public String getIndicators() {
        return env.getProperty("yahoo.indicators");
    }

    public String isTimestampsIncluded() {
        return env.getProperty("yahoo.include.timestamp");
    }

}
