package com.mibaldi.retorssandroid.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class NetworkHelper {
    private static NetworkHelper instance = new NetworkHelper();
    static Context context;
    ConnectivityManager connectivityManager;
    boolean connected = false;

    public static NetworkHelper getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isConnected() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;

    }
}
