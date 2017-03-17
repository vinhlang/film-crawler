package com.loser.film.crawler.config;

/**
 *
 * @author VinhNV
 */
public class MovieProp extends BaseParser {

    private String name;
    private String propType;

    public MovieProp(String wrapper, String wraperType, String name, String propType) {
        super(wrapper, wraperType);
        this.name = name;
        this.propType = propType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

}
