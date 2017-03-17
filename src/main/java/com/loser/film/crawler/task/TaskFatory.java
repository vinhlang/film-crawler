package com.loser.film.crawler.task;

import java.util.concurrent.ThreadFactory;

/**
 *
 * @author VinhNV
 */
public class TaskFatory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
//        if (r instanceof MovieParsing) {
//            MovieParsing movieParsing = (MovieParsing) r;
//            return new Thread(movieParsing, movieParsing.getTaskName());
//        }
        return null;
    }

}
