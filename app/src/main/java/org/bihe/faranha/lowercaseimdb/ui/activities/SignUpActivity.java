package org.bihe.faranha.lowercaseimdb.ui.activities;

import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_EMAIL;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_GENDER;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_ID;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_NAME;
import static org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager.PREF_KEY_PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.bihe.faranha.lowercaseimdb.data.db.DbManager;
import org.bihe.faranha.lowercaseimdb.data.db.DbResponse;
import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.thread.InsertUserRunnable;
import org.bihe.faranha.lowercaseimdb.utils.EmailValidator;
import org.bihe.faranha.lowercaseimdb.utils.FormValidator;
import org.bihe.faranha.lowercaseimdb.data.objects.User;
import org.bihe.faranha.lowercaseimdb.databinding.ActivitySignUpBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private Toolbar mToolbar;
    EditText yourName;
    EditText yourEmail;
    EditText yourPass;
    EditText yourRePass;
    RadioGroup radioGroup;
    CheckBox agreeCheck;

    Toast passErrorToast;
    Toast emailErrorToast;
    Toast agreementToast;
    Toast formError;

    private ExecutorService executorService;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        executorService = Executors.newCachedThreadPool();

        yourName = binding.yourNameEt;
        yourEmail = binding.yourEmailEt;
        yourPass = binding.yourPasswordEt;
        yourRePass = binding.yourRepasswordEt;
        radioGroup = binding.radioGroup;
        agreeCheck = binding.agreeCheck;
        passErrorToast = Toast.makeText
                (this, "Passwords Don't Match!", Toast.LENGTH_SHORT);
        emailErrorToast = Toast.makeText
                (this, "The Email Address Is Not Valid!", Toast.LENGTH_SHORT);
        agreementToast = Toast.makeText
                (this, "You Must Agree To Terms In Order To Sign Up!", Toast.LENGTH_SHORT);
        formError = Toast.makeText
                (this, "You Must Fill Out The Form In Order To Sign Up!", Toast.LENGTH_SHORT);
    }

    public void signUp(View view) {
        String name = yourName.getText().toString();
        String email = yourEmail.getText().toString();
        String pass = yourPass.getText().toString();
        String rePass = yourRePass.getText().toString();
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton selected = findViewById(radioId);
        String gender = "";
        if (selected == null) {
            formError.show();
        } else {
            gender = selected.getText().toString();
        }
        if (FormValidator.hasNullOrEmpty(name, email, pass, rePass, gender)) {
            formError.show();
        } else if (!pass.equals(rePass)) {
            passErrorToast.show();
        } else if (!agreeCheck.isChecked()) {
            agreementToast.show();
        } else if (!EmailValidator.isValidEmail(email)) {
            emailErrorToast.show();
        } else {
            User newUser = new User(name,pass,email,gender);
                executorService.execute(new InsertUserRunnable(getApplicationContext(), newUser, email,new DbResponse<User>() {
                    @Override
                    public void onSuccess(User user) {
                        runOnUiThread(() -> Toast.makeText
                                        (SignUpActivity.this, "Account Created Succesfuly!", Toast.LENGTH_SHORT)
                                .show());

                        PreferencesManager preferencesManager = PreferencesManager.getInstance(getApplicationContext());
                        preferencesManager.put(PREF_KEY_NAME, name);
                        preferencesManager.put(PREF_KEY_EMAIL, email);
                        preferencesManager.put(PREF_KEY_PASSWORD, pass);
                        preferencesManager.put(PREF_KEY_GENDER, newUser.getGender());
                        preferencesManager.put(PREF_KEY_ID, String.valueOf(newUser.getUserID()));
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onError(Error error) {
                        runOnUiThread(() -> Toast.makeText
                                        (SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT)
                                .show());
                    }
                }));
            }
    }

    public void backToMain(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}