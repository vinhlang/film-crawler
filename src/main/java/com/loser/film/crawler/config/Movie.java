package com.loser.film.crawler.config;

import java.util.List;

/**
 *
 * @author VinhNV
 */
public class Movie extends BaseParser {

    private List<MovieProp> filmProps;
    private String posterWrapper;
    private String episodeWrapper;

    public Movie(List<MovieProp> filmProps, String poster, String wrapper, String wraperType, String episodeWrapper) {
        super(wrapper, wraperType);
        this.filmProps = filmProps;
        this.posterWrapper = poster;
        this.episodeWrapper = episodeWrapper;
    }

    public String getPoster() {
        return posterWrapper;
    }

    public void setPoster(String poster) {
        this.posterWrapper = poster;
    }

    public List<MovieProp> getFilmProps() {
        return filmProps;
    }

    public void setFilmProps(List<MovieProp> filmProps) {
        this.filmProps = filmProps;
    }

    public String getPosterWrapper() {
        return posterWrapper;
    }

    public void setPosterWrapper(String posterWrapper) {
        this.posterWrapper = posterWrapper;
    }

    public String getEpisodeWrapper() {
        return episodeWrapper;
    }

    public void setEpisodeWrapper(String episodeWrapper) {
        this.episodeWrapper = episodeWrapper;
    }

}
