package org.bihe.faranha.lowercaseimdb.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.bihe.faranha.lowercaseimdb.databinding.ActivityCategoriesBinding;

public class CategoriesActivity extends AppCompatActivity {

    private ActivityCategoriesBinding binding;
    private Toolbar mToolbar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void backToHome(View view) {
        finish();
    }

    public void showNews(View view) {
        intent = new Intent(getApplicationContext(),NewsActivity.class);
        startActivity(intent);
    }

    public void showCelebs(View view) {
        intent = new Intent(getApplicationContext(),CelebsActivity.class);
        startActivity(intent);
    }

    public void showMovies(View view) {
        intent = new Intent(getApplicationContext(),MoviesActivity.class);
        startActivity(intent);
    }

    public void showShows(View view) {
        intent = new Intent(getApplicationContext(),ShowsActivity.class);
        startActivity(intent);
    }

    public void showEvents(View view) {
        intent = new Intent(getApplicationContext(),EventsActivity.class);
        startActivity(intent);
    }
}