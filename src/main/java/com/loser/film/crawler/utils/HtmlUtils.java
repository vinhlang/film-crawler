package com.loser.film.crawler.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author VinhNV
 */
public class HtmlUtils {

    public static String getLink(Element element) {
        return element.attr("href");
    }

    public static String getText(Element element) {
        return element.text();
    }

    public static Elements getElements(Document doc, String grep, String type) {
        switch (type) {
            case "class":
                return doc.getElementsByClass(grep);
            case "tag":
                return doc.getElementsByTag(grep);
        }
        return null;
    }

    public static Element getElement(Document doc, String id) {
        return doc.getElementById(id);
    }
}
