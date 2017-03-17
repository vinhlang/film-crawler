package com.loser.film.crawler.config;

import com.loser.core.textbase.ConfigsReader;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.w3c.dom.Element;

/**
 *
 * @author VinhNV
 */
public class XmlConfig extends ConfigsReader {

    public static ConfigMapping configMapping;

    public synchronized static ConfigMapping getConfigMapping() {
        return configMapping;
    }

    @Override
    protected void readPropeties() throws Exception {
        ArrayList<Site> sites = new ArrayList<>();
        ArrayList<Element> siteElements = (ArrayList<Element>) getListElement("site");
        BlockingQueue<Category> categorys = new LinkedBlockingDeque<>();
        int sleepTime = 5000;
        for (Element siteElement : siteElements) {
            sleepTime = getInt(siteElement, "sleep_time");
            String listMovieWrapper = getString(siteElement, "list_movie_wrapper");
            String listMovieWrapperType = getString(siteElement, "list_movie_wrapper_type");
            String domain = getStringAttr(siteElement, "domain");
            ArrayList<Element> cateElements = (ArrayList<Element>) getListElement(siteElement, "category|item");
            for (Element cateElement : cateElements) {
                String startUrl = getString(cateElement, "start_url");
                String nextPageToken = getString(cateElement, "next_page_token");
                String categoryName = getString(cateElement, "category_name");
                Category category = new Category(startUrl, nextPageToken, listMovieWrapper, listMovieWrapperType, categoryName);
                categorys.add(category);
            }
            Site site = new Site(domain, categorys, null);

            ArrayList<Element> movieElements = (ArrayList<Element>) getListElement(siteElement, "movies");
            for (Element movieElement : movieElements) {
                String movieItemWrapper = getString(movieElement, "movie_item_wrapper");
                String movieItemWrapperType = getString(movieElement, "movie_item_wrapper_type");
                String moviePoster = getString(movieElement, "poster_wrapper");
                String movieEpisodeWrapper = getString(movieElement, "episode_wrapper");
                ArrayList<MovieProp> movieProps = new ArrayList<>();
                Movie movie = new Movie(movieProps, moviePoster, movieItemWrapper, movieItemWrapperType, movieEpisodeWrapper);
                ArrayList<Element> moviePropElements = (ArrayList<Element>) getListElement(movieElement, "item");
                for (Element moviePropElement : moviePropElements) {
                    String name = getString(moviePropElement, "name");
                    String wrapper = getString(moviePropElement, "wrapper");
                    String movieWrapperType = getString(moviePropElement, "wrapper_type");
                    String propType = getString(moviePropElement, "prop_type");
                    MovieProp movieProp = new MovieProp(wrapper, movieWrapperType, name, propType);
                    movieProps.add(movieProp);
                }
                site.setMovie(movie);
            }
            sites.add(site);
        }
        configMapping = new ConfigMapping(sites, sleepTime);
    }

}
