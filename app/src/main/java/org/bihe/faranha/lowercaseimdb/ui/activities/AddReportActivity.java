package org.bihe.faranha.lowercaseimdb.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.thread.GetAllReportsRunnable;
import org.bihe.faranha.lowercaseimdb.thread.InsertReportRunnable;
import org.bihe.faranha.lowercaseimdb.utils.ApplicationManager;
import org.bihe.faranha.lowercaseimdb.utils.FormValidator;
import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.R;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapter;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapterHome;
import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityAddReportBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddReportActivity extends AppCompatActivity {

    private ActivityAddReportBinding binding;

    private ReportAdapterHome reportAdapterHome;
    private ReportAdapter reportAdapter;
    private ExecutorService executorService;
    Spinner spinner;
    EditText titleEt;
    EditText contentEt;

    EditText imageUrlET;
    Toast formError;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        titleEt = binding.titleEt;
        contentEt = binding.contentEt;
        spinner = binding.categorySpinner;
        imageUrlET = binding.urlEt;

        executorService = Executors.newCachedThreadPool();
        getAllReports();
        ArrayList<Report> reports = new ArrayList<Report>();
        reportAdapterHome = new ReportAdapterHome(reports,this);
        reportAdapter = new ReportAdapter(reports, this);

        formError = Toast.makeText
                (this, "You Must Fill Out The Form In Order To Add A Report!", Toast.LENGTH_SHORT);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void backFromAddReport(View view) {
        finish();
    }
    public void addReport(View view) throws MalformedURLException {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        ReportCategory category = ReportCategory.valueOf(spinner.getSelectedItem().toString());
        String image = imageUrlET.getText().toString();
        Uri imageUrl = Uri.parse(image);
        PreferencesManager preferencesManager = PreferencesManager.getInstance(getApplicationContext());
        String storedName = preferencesManager.get(PreferencesManager.PREF_KEY_NAME, "");
        if (FormValidator.hasNullOrEmpty(title,content,String.valueOf(category))) {
            formError.show();
        } else {
            Report newReport = new Report(storedName,title,content,category, Date.from(Instant.now()),imageUrl);

            executorService.execute(new InsertReportRunnable(getApplicationContext(), newReport, new DbResponse<Report>() {
                @Override
                public void onSuccess(Report report) {
                    runOnUiThread(() -> reportAdapter.add(newReport));
                    runOnUiThread(() -> reportAdapterHome.add(newReport));
                }

                @Override
                public void onError(Error error) {
                    runOnUiThread(() -> Toast.makeText(AddReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT)
                            .show());

                }
            }));
//            reportAdapterHome = new ReportAdapterHome(ApplicationManager.allReports,this);
//            reportAdapter = new ReportAdapter(ApplicationManager.allReports, this);
//            reportAdapterHome.add(newReport);
//            reportAdapter.add(newReport);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    public void getAllReports() {
        executorService.execute(new GetAllReportsRunnable(getApplicationContext(), new DbResponse<List<Report>>() {
            @Override
            public void onSuccess(List<Report> reports) {
                runOnUiThread(() -> reportAdapterHome.updateAll(reports));
            }

            @Override
            public void onError(Error error) {
                runOnUiThread(() -> Toast.makeText(AddReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }));
    }
}