package org.bihe.faranha.lowercaseimdb.thread;

import android.content.Context;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.data.objects.User;

public class InsertReportRunnable implements Runnable{

    private final DbManager dbManager;

    private DbResponse<Report> dbResponse;
    private Report report;

    public InsertReportRunnable(Context context, Report report, DbResponse<Report> dbResponse) {
        this.dbManager = DbManager.getInstance(context);
        this.report = report;
        this.dbResponse = dbResponse;
    }

    @Override
    public void run() {
        long id = dbManager.reportDao().insert(report);
        if (id>0) {
            report.setId((int)id);
            dbResponse.onSuccess(report);
        } else {
            Error error = new Error("Something Went Wrong!");
            dbResponse.onError(error);
        }
    }
}
