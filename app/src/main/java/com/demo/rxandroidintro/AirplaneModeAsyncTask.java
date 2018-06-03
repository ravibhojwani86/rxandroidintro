package com.demo.rxandroidintro;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by ravi .
 */

public class AirplaneModeAsyncTask extends AsyncTask<Void, Void, Boolean>{

    private Context mContext;
    private Callback mCallback;

    AirplaneModeAsyncTask(Context context, Callback callback){
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return DbUtils.isAirplaneModeOn(mContext);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mCallback.setAirplaneMode(result);
    }
}
