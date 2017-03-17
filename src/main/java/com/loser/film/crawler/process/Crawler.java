package com.loser.film.crawler.process;

import com.loser.core.textbase.Log4jLoader;
import com.loser.core.threads.ThreadBase;
import com.loser.film.crawler.config.Category;
import com.loser.film.crawler.config.Site;
import com.loser.film.crawler.config.XmlConfig;
import com.loser.film.crawler.task.MovieParsing;
import com.loser.film.crawler.utils.HttpClientUtils;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.log4j.Logger;

/**
 *
 * @author VinhNV
 */
public class Crawler extends ThreadBase {

    private final Logger logger;
    private Site site;
    private ExecutorService executors;
    private int nextPageIndex;

    public Crawler(Site site) {
        super("Crawler");
        logger = Log4jLoader.getLogger("crawler");
        initPoolProcess();
        this.site = site;

        this.nextPageIndex = 1;
    }

    @Override
    protected void onExecuting() throws Exception {
        logger.info("[onExecuting]");
    }

    @Override
    protected void onKilling() {
        logger.info("[onKilling]");
    }

    @Override
    protected void onException(Exception e) {
        logger.info("[onException]>>" + e.getMessage(), e);
    }

    @Override
    protected long sleeptime() throws Exception {
        return XmlConfig.getConfigMapping().getSleepTime();
    }

    @Override
    protected void action() throws Exception {
        Category category = site.getCategorys().take();
        String nexUrl = category.getStartUrl();
        while (!nexUrl.isEmpty()) {
            logger.info("====>Start process with url:" + nexUrl);
            String content = HttpClientUtils.getInstance(5000).get("", nexUrl, site.getDomain());
            if (content != null && !content.isEmpty()) {
                Document doc = Jsoup.parse(content, "UTF-8");
                Elements contenElements = null;
                switch (category.getWraperType()) {
                    case "class":
                        contenElements = doc.getElementsByClass(category.getWrapper());
                        break;
                    case "tag":
                        Elements temps = doc.getElementsByTag(category.getWrapper());
                        contenElements = new Elements();
                        for (Element temp : temps) {
                            if (temp.className().isEmpty()) {
                                contenElements.add(temp);
                            }
                        }
                        break;
                }
                if (contenElements == null) {
                    continue;
                }
                if (process(contenElements, category)) {
                    nextPageIndex++;
                    nexUrl = category.getStartUrl() + category.getNexPageToken().replaceAll("\\$next", Integer.toString(nextPageIndex));
                    logger.info("====>Next url:" + nexUrl);
                    Thread.sleep(XmlConfig.getConfigMapping().getSleepTime());
                    continue;
                }
                nexUrl = "";
            }
        }
    }

    private boolean process(Elements contenElements, Category category) {
        boolean found = false;
        for (Element contenElement : contenElements) {
            Elements elements = contenElement.getElementsByClass(site.getMovie().getWrapper());
            if (elements.isEmpty()) {
                continue;
            }
            found = true;
            elements.forEach((element) -> {
                Elements listMovieElements = element.getElementsByClass(site.getMovie().getWrapper());
                listMovieElements.forEach((listMovieElement) -> {
                    String url = getUrl(listMovieElement);
                    executors.execute(new MovieParsing(site.getMovie(), url, site.getDomain(), category.getName()));
                });
            });
        }
        return found;
    }

    private String getUrl(Element element) {
        Elements elements = element.getElementsByTag("a");
        if (!elements.isEmpty()) {
            String href = elements.get(0).attr("href");
            logger.info("[href]:" + href);
            return href;
        }
        return "";
    }

    private void initPoolProcess() {
//        BlockingQueue<Runnable> queuPool = new LinkedBlockingQueue<>();
        this.executors = Executors.newFixedThreadPool(12);
    }
}
