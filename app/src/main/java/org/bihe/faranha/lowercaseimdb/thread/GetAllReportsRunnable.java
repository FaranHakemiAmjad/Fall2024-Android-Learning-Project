package org.bihe.faranha.lowercaseimdb.thread;

import android.content.Context;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;

import java.util.List;

public class GetAllReportsRunnable implements Runnable {

    private final DbManager dbManager;

    private DbResponse<List<Report>> dbResponse;

    public GetAllReportsRunnable(Context context, DbResponse<List<Report>> dbResponse) {
        this.dbManager = DbManager.getInstance(context);
        this.dbResponse = dbResponse;
    }
    @Override
    public void run() {
        List<Report> reports = dbManager.reportDao().getAll();
        if(reports==null) {
            dbResponse.onError(new Error("Something Went Wrong!"));
        } else {
            dbResponse.onSuccess(reports);
        }
    }
}
