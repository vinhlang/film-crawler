package com.loser.film.crawler.config;

import java.util.List;

/**
 *
 * @author VinhNV
 */
public class ConfigMapping {

    private List<Site> sites;
    private int sleepTime;

    public ConfigMapping() {
    }

    public ConfigMapping(List<Site> sites, int sleepTime) {
        this.sites = sites;
        this.sleepTime = sleepTime;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setSite(List<Site> sites) {
        this.sites = sites;
    }

}
