package org.bihe.faranha.lowercaseimdb.thread;

import android.content.Context;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;

import java.util.List;

public class GetAllUserReportsRunnable implements Runnable {

    private final DbManager dbManager;
    private DbResponse<List<Report>> dbResponse;
    private String sender;

    public GetAllUserReportsRunnable(Context context, String sender,DbResponse<List<Report>> dbResponse) {
        this.dbManager = DbManager.getInstance(context);
        this.sender = sender;
        this.dbResponse = dbResponse;
    }
    @Override
    public void run() {
        List<Report> reports = dbManager.reportDao().getAllUserReports(sender);
        if(reports==null) {
            dbResponse.onError(new Error("Something Went Wrong!"));
        } else {
            dbResponse.onSuccess(reports);
        }
    }
}
