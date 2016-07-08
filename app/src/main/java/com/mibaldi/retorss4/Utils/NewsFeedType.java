package com.mibaldi.retorss4.Utils;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public enum NewsFeedType {
    PORTADA,ESPAÑA,DEPORTE;
    public static NewsFeedType NewsFeedTypeFromInt(int type){
        if ( type == 0){
            return NewsFeedType.PORTADA;
        }else if (type == 1){
            return  NewsFeedType.ESPAÑA;
        }
        else{
            return NewsFeedType.DEPORTE;
        }
    }
}
