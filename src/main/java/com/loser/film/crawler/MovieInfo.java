package com.loser.film.crawler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author VinhNV
 */
public class MovieInfo {

    private String title;
    private String url;
    private String thumbnal;
    private float rate;
    private Map<String, String> episode;
    private String category;

    public MovieInfo() {
        episode = new ConcurrentHashMap<>();
    }

    public MovieInfo(String url, String title, String thumbnal, float rate) {
        this.url = url;
        this.title = title;
        this.thumbnal = thumbnal;
        this.rate = rate;
    }

    public MovieInfo(String url, String title, String thumbnal, float rate, Map<String, String> episode) {
        this.url = url;
        this.title = title;
        this.thumbnal = thumbnal;
        this.rate = rate;
        this.episode = episode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnal() {
        return thumbnal;
    }

    public void setThumbnal(String thumbnal) {
        this.thumbnal = thumbnal;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Map<String, String> getEpisode() {
        return episode;
    }

    public void setEpisode(Map<String, String> episode) {
        this.episode = episode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "MovieInfo{" + "url=" + url + ", title=" + title + ", thumbnal=" + thumbnal + ", rate=" + rate + ", episode=" + episode.toString() + '}';
    }

}
