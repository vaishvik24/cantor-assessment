package com.canter.stocks.analysis.canterdemo.controller;

import com.canter.stocks.analysis.canterdemo.controllers.StocksController;
import com.canter.stocks.analysis.canterdemo.models.StockAnalysis;
import com.canter.stocks.analysis.canterdemo.services.StocksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StocksController.class)
public class StocksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StocksService stocksService;

    @Test
    public void retrieveDetailsForCourse() throws Exception {

        Map<String, List<StockAnalysis>> mockitoResult = new HashMap<>();
        mockitoResult.put("AMZN",
                Arrays.asList(
                        new StockAnalysis("2023-02-03", "-8.43"),
                        new StockAnalysis("2023-02-02", "7.38"),
                        new StockAnalysis("2023-01-11", "5.81")
                )
        );
        Mockito.when(
                        stocksService.getStockAnalysisReport(Mockito.any(), Mockito.anyString()))
                .thenReturn(mockitoResult);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/sampleAPI/analysis?tickers=AMZN&range=3mo").accept(
                MediaType.APPLICATION_JSON
        ).with(httpBasic("name1", "password1"));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"AMZN\":[{\"date\":\"2023-02-03\",\"move\":\"-8.43\"},{\"date\":\"2023-02-02\",\"move\":\"7.38\"},{\"date\":\"2023-01-11\",\"move\":\"5.81\"}]}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}
