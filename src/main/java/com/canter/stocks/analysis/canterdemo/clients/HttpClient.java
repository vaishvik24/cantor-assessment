package com.canter.stocks.analysis.canterdemo.clients;

import com.canter.stocks.analysis.canterdemo.controllers.StocksController;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public class HttpClient {

    // Http client to be used for GET/POST/PUT API
    public HttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    }

    Logger logger = LoggerFactory.getLogger(StocksController.class);

    // This resolves SSL related issue
    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    // This resolves SSL related issue
    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, TrustSelfSignedStrategy.INSTANCE).build();
    SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

    // Returns HTTP client with SSL disabled
    CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }

    // Simple GET request on the given URL
    String getRequest(String url) {
        HttpGet request = new HttpGet(url);
        logger.info("Method GET: " + url);
        try (CloseableHttpClient httpClient = getHttpClient(); CloseableHttpResponse response = httpClient.execute(request)) {
            logger.debug("Response retrieved: " + response);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error("Failed to get resource from yahoo finance client. ERROR: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

}
