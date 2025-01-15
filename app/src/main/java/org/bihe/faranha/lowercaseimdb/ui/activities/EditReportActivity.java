package org.bihe.faranha.lowercaseimdb.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.bihe.faranha.lowercaseimdb.utils.ApplicationManager;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapterHome;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityEditReportBinding;

public class EditReportActivity extends AppCompatActivity {

    private ActivityEditReportBinding binding;
    private ReportAdapterHome reportAdapterHome;
    private EditText editTextTitle;
    private EditText editTextBody;
    private Spinner spinner;

    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        editTextTitle = binding.titleEditEt;
        editTextBody = binding.contentEditEt;
        spinner = binding.categorySpinner;
//        Report report = ApplicationManager.getReport(editTextTitle.getText().toString(), editTextBody.getText().toString());
    }

    public void backFromEditReport(View view) {
        finish();
    }

    public void editReport(View view) {

//        reportAdapter = new ReportAdapter(ApplicationManager.allReports,this);
    }
}