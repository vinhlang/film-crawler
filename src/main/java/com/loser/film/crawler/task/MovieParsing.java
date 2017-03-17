package com.loser.film.crawler.task;

import com.google.gson.Gson;
import com.loser.core.textbase.Log4jLoader;
import com.loser.core.utils.FileUtils;
import com.loser.film.crawler.MovieInfo;
import com.loser.film.crawler.config.Movie;
import com.loser.film.crawler.config.MovieProp;
import com.loser.film.crawler.config.XmlConfig;
import com.loser.film.crawler.utils.HttpClientUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.log4j.Logger;

/**
 *
 * @author VinhNV
 */
public class MovieParsing implements Runnable {

    private final Movie movie;
    private final Logger logger;
    private final String url;
    private final String domain;
    private final int sleep;
    private final String categoryName;

    public MovieParsing(Movie movie, String url, String domain, String categoryName) {
        this.movie = movie;
        this.domain = domain;
        this.url = domain + url;
        logger = Log4jLoader.getLogger("crawler");
        this.sleep = XmlConfig.getConfigMapping().getSleepTime();
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        logger.info("[Get movie info from=>]" + url);
        String rootContent = HttpClientUtils.getInstance(this.sleep).get(url, url, domain);
        Document doc = Jsoup.parse(rootContent, "UTF-8");
//        doc.outputSettings().charset().forName("UTF-8");
//        doc.outputSettings().escapeMode(EscapeMode.xhtml);
        Elements elements = doc.getElementsByClass(movie.getPoster());
        if (!elements.isEmpty()) {
            try {
                MovieInfo movieInfo = new MovieInfo();
                Element posterElement = elements.get(0);
                getElementProp(posterElement, movieInfo);
                movieInfo.setCategory(categoryName);
                File file = new File("./data/" + movieInfo.getCategory() + ".txt");
                Gson gson = new Gson();
                FileUtils.writeLine(file, gson.toJson(movieInfo), true);
//                logger.info(movieInfo.toString());
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    private void getElementProp(Element element, MovieInfo movieInfo) {
        List<MovieProp> movieProps = movie.getFilmProps();
        movieProps.forEach((movieProp) -> {
            getMovieProp(movieProp, element, movieInfo);
        });
    }

    private void getMovieProp(MovieProp movieProp, Element element, MovieInfo movieInfo) {
        Elements elements;
        switch (movieProp.getPropType()) {
            case "image":
                elements = element.getElementsByTag("img");
                if (!elements.isEmpty()) {
                    String src = elements.get(0).attr("src");
                    movieInfo.setThumbnal(src);
                    break;
                }
            case "link":
                elements = element.getElementsByTag("a");
                if (!elements.isEmpty()) {
                    String href = elements.get(0).attr("href");
                    movieInfo.setUrl(href);
                    String rootContent = HttpClientUtils.getInstance(this.sleep).get(href, domain + href, domain);
                    Document doc = Jsoup.parse(rootContent, "UTF-8");
//                    doc.outputSettings().charset().forName("UTF-8");
//                    doc.outputSettings().escapeMode(EscapeMode.xhtml);
                    Elements episodeElements = doc.getElementsByClass(movie.getEpisodeWrapper());
                    if (!episodeElements.isEmpty()) {
                        Map<String, String> episodes = getEpisodes(episodeElements.get(0));
                        movieInfo.setEpisode(episodes);
                    }
                    break;
                }
                break;
            case "text":
                elements = element.getElementsByClass(movieProp.getWrapper());
                if (!elements.isEmpty()) {
                    String text = elements.get(0).text();
                    movieInfo.setTitle(text);
                    break;
                }
                break;
        }
    }

    private Map<String, String> getEpisodes(Element element) {
        Elements episodeElements = element.getElementsByTag("a");
        Map<String, String> result = new ConcurrentHashMap<>();
        episodeElements.forEach((episodeElement) -> {
            String link = episodeElement.attr("href");
            String ep = episodeElement.text();
            result.put(ep, link);
        });
        return result;
    }

}
