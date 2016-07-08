package com.mibaldi.retorss3.Utils;

import com.mibaldi.retorss3.Activities.NoticiaListActivity;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class NewsFeedUtils {
    public static void applyNewsFeed(NewsFeedType newsfeed) {
        String url = "";
        switch (newsfeed){
            case PORTADA:
                url ="http://ep00.epimg.net/rss/ccaa/andalucia.xml";
                break;
            case ESPAÑA:
                url ="http://ep00.epimg.net/rss/elpais/portada.xml";
                break;
            case DEPORTE:
                url = "http://ep00.epimg.net/rss/deportes/portada.xml";
                break;
        }
        NoticiaListActivity.URL2 = url;
    }
}
