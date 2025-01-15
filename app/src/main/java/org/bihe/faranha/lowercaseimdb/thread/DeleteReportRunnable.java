package org.bihe.faranha.lowercaseimdb.thread;

import android.content.Context;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;

public class DeleteReportRunnable implements Runnable{

    private DbManager dbManager;
    private DbResponse<Long> dbResponse;
    private long id;

    public DeleteReportRunnable(Context context, long id, DbResponse<Long> dbResponse) {
        this.dbManager = DbManager.getInstance(context);
        this.id = id;
        this.dbResponse = dbResponse;
    }
    @Override
    public void run() {
        int numberOfAffectedRows = dbManager.reportDao().delete(id);
        if(numberOfAffectedRows>0) {
            dbResponse.onSuccess(id);
        } else {
            dbResponse.onError(new Error("Something Went Wrong!"));
        }
    }
}
