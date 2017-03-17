package com.loser.film.crawler.config;

/**
 *
 * @author VinhNV
 */
public class BaseParser {

    private String wrapper;
    private String wraperType;

    public BaseParser(String wrapper, String wraperType) {
        this.wrapper = wrapper;
        this.wraperType = wraperType;
    }

    public String getWrapper() {
        return wrapper;
    }

    public void setWrapper(String wrapper) {
        this.wrapper = wrapper;
    }

    public String getWraperType() {
        return wraperType;
    }

    public void setWraperType(String wraperType) {
        this.wraperType = wraperType;
    }

}
