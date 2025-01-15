package org.bihe.faranha.lowercaseimdb.ui.activities;

import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_EMAIL;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_NAME;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.thread.DeleteReportRunnable;
import org.bihe.faranha.lowercaseimdb.thread.GetAllUserReportsRunnable;
import org.bihe.faranha.lowercaseimdb.utils.ApplicationManager;
import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapter;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapterHome;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityEditReportBinding;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityProfileBinding;
import org.bihe.faranha.lowercaseimdb.databinding.ReportActionRequestBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREF_KEY_GENDER = "";
    ActivityProfileBinding binding;
    ReportActionRequestBinding actionRequestBinding;
    ActivityEditReportBinding activityEditReportBinding;
    ReportAdapterHome reportAdapterHome;
    ReportAdapter reportAdapter;
    private ExecutorService executorService;
    TextView name;
    TextView email;
    TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        executorService = Executors.newCachedThreadPool();
        setProfileData();
        getAllUserReports();
        setUpRecyclerView();
    }

    public void backFromProfile(View view) {
        finish();
    }

    public void setProfileData() {
        name = binding.fullNameTv;
        email = binding.fullEmailTv;
        gender = binding.fullGenderTv;
        PreferencesManager preferencesManager = PreferencesManager.getInstance(getApplicationContext());
        name.setText(preferencesManager.get(PREF_KEY_NAME, ""));
        email.setText(preferencesManager.get(PREF_KEY_EMAIL, ""));
        gender.setText(preferencesManager.get(PREF_KEY_GENDER, ""));
    }

    public void getAllUserReports() {
        PreferencesManager preferencesManager = PreferencesManager.getInstance(getApplicationContext());
        String storedName = preferencesManager.get(PREF_KEY_NAME, "");
        executorService.execute(new GetAllUserReportsRunnable(getApplicationContext(), storedName, new DbResponse<List<Report>>() {
            @Override
            public void onSuccess(List<Report> reports) {
                runOnUiThread(() -> reportAdapter.updateAll(reports));
            }

            @Override
            public void onError(Error error) {
                runOnUiThread(() -> Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }));
    }

    public void setUpRecyclerView() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ReportActionRequestBinding reportActionRequestBinding = ReportActionRequestBinding.inflate(getLayoutInflater());
        View dialogRoot = reportActionRequestBinding.getRoot();
        builder.setView(dialogRoot);
        final AlertDialog alertDialog = builder.create();

        ArrayList<Report> reports = new ArrayList<Report>();
        reportAdapterHome = new ReportAdapterHome(reports, this);
        reportAdapter = new ReportAdapter(reports, this, new ReportAdapter.ReportAdapterCallback() {
            @Override
            public void onReportLongClicked(int position) {
                alertDialog.show();
                reportActionRequestBinding.deleteRequestBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long id = (long) position + 1;
                        executorService.execute(new DeleteReportRunnable(getApplicationContext(), id, new DbResponse<Long>() {
                            @Override
                            public void onSuccess(Long id) {
                                runOnUiThread(() -> reportAdapter.remove(id));
                                runOnUiThread(() -> reportAdapterHome.remove(id));
                            }

                            @Override
                            public void onError(Error error) {
                                runOnUiThread(() -> Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());

                            }
                        }));
                        reportAdapter.remove(position);
                        reportAdapterHome.remove(position);
                        alertDialog.dismiss();
                    }
                });
                reportActionRequestBinding.editRequestBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), EditReportActivity.class);
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                });
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(reportAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}