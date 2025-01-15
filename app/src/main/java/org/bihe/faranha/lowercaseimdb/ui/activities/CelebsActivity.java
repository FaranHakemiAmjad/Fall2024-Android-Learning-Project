package org.bihe.faranha.lowercaseimdb.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.thread.GetAllReportCategoryRunnable;
import org.bihe.faranha.lowercaseimdb.utils.ApplicationManager;
import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapter;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapterHome;
import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityCelebsBinding;
import org.bihe.faranha.lowercaseimdb.databinding.ReportActionRequestBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CelebsActivity extends AppCompatActivity {

    ActivityCelebsBinding binding;
    ReportAdapter reportAdapter;
    ReportAdapterHome reportAdapterHome;
    ReportActionRequestBinding reportActionRequestBinding;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCelebsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        executorService = Executors.newCachedThreadPool();
        getReports();
        setUpRecyclerView();
    }

    public void backFromCelebs(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    public void setUpRecyclerView() {
        ArrayList<Report> reports = new ArrayList<Report>();
        reportAdapter = new ReportAdapter(reports, this);
        binding.recyclerViewCelebs.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCelebs.setAdapter(reportAdapter);
    }

    public void getReports() {
        executorService.execute(new GetAllReportCategoryRunnable(getApplicationContext(), ReportCategory.CELEBS, new DbResponse<List<Report>>() {
            @Override
            public void onSuccess(List<Report> reports) {
                runOnUiThread(() -> reportAdapter.updateAll(reports));
            }

            @Override
            public void onError(Error error) {
                runOnUiThread(() -> Toast.makeText(CelebsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }));
    }
}