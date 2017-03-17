package com.loser.film.crawler.config;

import java.util.List;

/**
 *
 * @author VinhNV
 */
public class Category extends BaseParser {

    private String name;
    private String startUrl;
    private String nexPageToken;

    public Category(String startUrl, String nexPageToken,String wrapper, String wraperType, String name) {
        super(wrapper, wraperType);
        this.startUrl = startUrl;
        this.nexPageToken = nexPageToken;
        this.name = name;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getNexPageToken() {
        return nexPageToken;
    }

    public void setNexPageToken(String nexPageToken) {
        this.nexPageToken = nexPageToken;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
