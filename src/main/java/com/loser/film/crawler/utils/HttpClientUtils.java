package com.loser.film.crawler.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import com.loser.core.textbase.Log4jLoader;
import org.apache.log4j.Logger;

public class HttpClientUtils {

    private HttpClient httpClient = null;
    private static Logger logger = Log4jLoader.getLogger();
    private static ConcurrentHashMap<String, HttpClientUtils> listInstance = new ConcurrentHashMap<String, HttpClientUtils>();

    private HttpClientUtils(int timeout) {
        try {
            MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
            HttpConnectionManagerParams params = new HttpConnectionManagerParams();
            params.setDefaultMaxConnectionsPerHost(800);
            params.setMaxTotalConnections(1500);
            params.setParameter(HttpConnectionManagerParams.SO_TIMEOUT, timeout);
            params.setParameter(HttpConnectionManagerParams.CONNECTION_TIMEOUT, timeout);
            connectionManager.setParams(params);
            httpClient = new HttpClient(connectionManager);
        } catch (Throwable e) {
            logger.info("Exception: " + e.getMessage());
        }
    }

    public synchronized static HttpClientUtils getInstance(int timeout) {
        HttpClientUtils tmp = listInstance.get(timeout + "");
        if (tmp == null) {
            tmp = new HttpClientUtils(timeout);
            listInstance.put(timeout + "", tmp);
        }
        return tmp;
    }

    public String get(String transid, String url, String domain) {
        GetMethod method = new GetMethod(url);
        method.setRequestHeader("Content-Type", "text/xml; charset=UTF-8");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 " + "(Windows NT 6.1; rv:10.0.2) " + "Gecko/20100101 Firefox/10.0.2");
        method.setRequestHeader("referer", domain);
        method.addRequestHeader("Connection", "close");
        try {
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                String rs = readText(method.getResponseBodyAsStream());
                // logger.debug(transid + ", get() method return value '" + rs + "'");
                return rs;
            } else {
                logger.info(transid + ", Request(GET) fail, url = " + url + "; respCode = " + method.getStatusCode());
            }
        } catch (Exception e) {
            logger.info(transid + ", url = " + url + ", Exception: " + e.getMessage(), e);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }

            httpClient.getHttpConnectionManager().closeIdleConnections(0);
            method = null;
        }
        return null;
    }

    public String readText(InputStream is) {
        InputStreamReader rd = null;
        BufferedReader in = null;
        try {
            rd = new InputStreamReader(is, "UTF-8");
            in = new BufferedReader(rd);
            String line;
            String all = "";
            while ((line = in.readLine()) != null) {
                all += "\n" + line;
            }
            all = all.replaceFirst("\n", "");
            return all;
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e2) {
                }
            }
            if (rd != null) {
                try {
                    rd.close();
                } catch (Exception e2) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e2) {
                }
            }
        }
        return null;
    }

}
