package com.partiallogic.ocw_android_2014;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by markholland on 21/08/14.
 */
public class DownloadDataTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = DownloadScheduleTask.class.getSimpleName();

    private final Context mContext;

    private ProgressDialog progress;

    public DownloadDataTask(Context context) {
        mContext = context;
        progress = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.setIndeterminate(true);
        progress.show();
    }

    @Override
    protected Void doInBackground(Void... params) {


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progress.dismiss();
    }
}