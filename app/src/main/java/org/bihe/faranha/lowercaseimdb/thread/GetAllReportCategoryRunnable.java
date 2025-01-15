package org.bihe.faranha.lowercaseimdb.thread;

import android.content.Context;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;

import java.util.List;

public class GetAllReportCategoryRunnable implements Runnable{
    private final DbManager dbManager;
    private DbResponse<List<Report>> dbResponse;
    private ReportCategory reportCategory;

    public GetAllReportCategoryRunnable(Context context, ReportCategory reportCategory, DbResponse<List<Report>> dbResponse) {
        this.dbManager = DbManager.getInstance(context);
        this.reportCategory = reportCategory;
        this.dbResponse = dbResponse;
    }
    @Override
    public void run() {
        List<Report> reports = dbManager.reportDao().getAllReportCategory(reportCategory);
        if(reports==null) {
            dbResponse.onError(new Error("Something Went Wrong!"));
        } else {
            dbResponse.onSuccess(reports);
        }
    }
}
