package com.demo.rxandroidintro;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by ravi .
 */

public class DbUtils {

    /**
     * Querying Settings for airplane mode status
     * */
    public static boolean isAirplaneModeOn(Context context){
        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}
