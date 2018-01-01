package com.beesham.shopifymerchantstore.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Beesham on 1/1/2018.
 */

public class NetworkUtils {
    /**
     * Checks for network availability
     * @param context to get ConnectivityManager
     * @returns true is network is available
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
