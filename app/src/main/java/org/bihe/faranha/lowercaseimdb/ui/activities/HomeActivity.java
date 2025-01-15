package org.bihe.faranha.lowercaseimdb.ui.activities;

import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.thread.GetAllReportsRunnable;
import org.bihe.faranha.lowercaseimdb.utils.ApplicationManager;
import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapterHome;
import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private ReportAdapterHome reportAdapterHome;
    private ExecutorService executorService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        executorService = Executors.newCachedThreadPool();
        getAllReports();
        setUpRecyclerView();
    }

    public void logOut(View view) {
        finish();
    }

    public void showProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    public void showCategories(View view) {
        Intent intent = new Intent(getApplicationContext(),CategoriesActivity.class);
        startActivity(intent);
    }

    public void toAddReport(View view) {
        Intent intent = new Intent(getApplicationContext(),AddReportActivity.class);
        startActivity(intent);
    }

    public void getAllReports() {
        executorService.execute(new GetAllReportsRunnable(getApplicationContext(), new DbResponse<List<Report>>() {
            @Override
            public void onSuccess(List<Report> reports) {
                runOnUiThread(() -> reportAdapterHome.updateAll(reports));
            }

            @Override
            public void onError(Error error) {
                runOnUiThread(() -> Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }));
    }

    public void setUpRecyclerView() {
        ArrayList<Report> reports = new ArrayList<Report>();
        reportAdapterHome = new ReportAdapterHome(reports, this, new ReportAdapterHome.ReportAdapterHomeCallback() {
            @Override
            public void onReportClicked(Report report) {
                ReportCategory category = report.getCategory();
                switch (category) {
                    case NEWS:
                        intent = new Intent(getApplicationContext(), NewsActivity.class);
                        startActivity(intent);
                        break;
                    case SHOWS:
                        intent = new Intent(getApplicationContext(), ShowsActivity.class);
                        startActivity(intent);
                        break;
                    case CELEBS:
                        intent = new Intent(getApplicationContext(), CelebsActivity.class);
                        startActivity(intent);
                        break;
                    case EVENTS:
                        intent = new Intent(getApplicationContext(), EventsActivity.class);
                        startActivity(intent);
                        break;
                    case MOVIES:
                        intent = new Intent(getApplicationContext(), MoviesActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        binding.recyclerViewHome.setLayoutManager(new GridLayoutManager(this,2, RecyclerView.VERTICAL,false));
        binding.recyclerViewHome.setAdapter(reportAdapterHome);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}

