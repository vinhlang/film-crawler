package com.loser.film.crawler.config;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author VinhNV
 */
public class Site {

    private String domain;
    private BlockingQueue<Category> categorys;
    private Movie movie;

    public Site(String domain, BlockingQueue<Category> categorys, Movie movie) {
        this.domain = domain;
        this.categorys = categorys;
        this.movie = movie;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public BlockingQueue<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(BlockingQueue<Category> categorys) {
        this.categorys = categorys;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
