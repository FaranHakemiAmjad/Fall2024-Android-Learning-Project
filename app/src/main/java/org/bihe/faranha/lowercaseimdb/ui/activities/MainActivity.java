package org.bihe.faranha.lowercaseimdb.ui.activities;

import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_EMAIL;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_GENDER;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_ID;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_NAME;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_PASSWORD;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.data.objects.User;
import org.bihe.faranha.lowercaseimdb.thread.FindUserRunnable;
import org.bihe.faranha.lowercaseimdb.thread.GetAllReportsRunnable;
import org.bihe.faranha.lowercaseimdb.ui.adapters.ReportAdapterHome;
import org.bihe.faranha.lowercaseimdb.utils.EmailValidator;
import org.bihe.faranha.lowercaseimdb.utils.FormValidator;
import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;
private SharedPreferences sharedPreferences;
private DbManager dbManager;
private ExecutorService executorService;
private ReportAdapterHome reportAdapterHome;

private User user;
    EditText emailEt;
    EditText pass;
    Toast formError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        executorService = Executors.newCachedThreadPool();

        emailEt = binding.emailEt;
        pass = binding.passwordEt;
        formError = Toast.makeText(this, "Invalid Email Or Password!", Toast.LENGTH_SHORT);

        PreferencesManager preferencesManager = PreferencesManager.getInstance(getApplicationContext());
        String storedEmail = preferencesManager.get(PreferencesManager.PREF_KEY_EMAIL, "");
        String storedPassword = preferencesManager.get(PreferencesManager.PREF_KEY_PASSWORD,"");
        String storedName = preferencesManager.get(PreferencesManager.PREF_KEY_NAME,"");
        String loggedInUserId = preferencesManager.get(PreferencesManager.PREF_KEY_ID, "");
        if (!FormValidator.hasNullOrEmpty(storedEmail, storedPassword)) {
            emailEt.setText(storedEmail);
            pass.setText(storedPassword);
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }
    }

    public void signIn(View view) {
        String email = emailEt.getText().toString();
        String password = pass.getText().toString();
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        executorService.execute(new FindUserRunnable(getApplicationContext(), email, new DbResponse<User>() {
            @Override
            public void onSuccess(User user) {
                if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    PreferencesManager preferencesManager = PreferencesManager.getInstance(getApplicationContext());
                    preferencesManager.put(PREF_KEY_NAME, user.getUsername());
                    preferencesManager.put(PREF_KEY_EMAIL, user.getEmail());
                    preferencesManager.put(PREF_KEY_PASSWORD, user.getPassword());
                    preferencesManager.put(PREF_KEY_GENDER, user.getGender());
                    preferencesManager.put(PREF_KEY_ID, String.valueOf(user.getUserID()));
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Error error) {
                formError.show();
            }
        }));
    }

    public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}