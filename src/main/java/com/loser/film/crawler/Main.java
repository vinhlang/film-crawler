package com.loser.film.crawler;

import com.loser.core.textbase.ConfigsReader;
import com.loser.core.textbase.Log4jLoader;
import com.loser.core.threads.AbsApplication;
import com.loser.film.crawler.config.Site;
import com.loser.film.crawler.config.XmlConfig;
import com.loser.film.crawler.process.Crawler;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author VinhNV
 */
public class Main extends AbsApplication {

    private static Logger logger;

    protected Main(Class<? extends ConfigsReader> clazzConfig) {
        super(clazzConfig);
    }

    @Override
    protected void initProcess() throws Exception {
        logger = Log4jLoader.getLogger("crawler");
        logger.info("=======START===============");
        List<Site> sites = XmlConfig.getConfigMapping().getSites();
        Site pbh = sites.get(0);
        new Crawler(pbh).execute();
    }

    @Override
    protected void endProcess() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        new Main(XmlConfig.class).start();
    }

}
