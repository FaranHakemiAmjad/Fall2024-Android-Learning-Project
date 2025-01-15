package org.bihe.faranha.lowercaseimdb.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.bihe.faranha.lowercaseimdb.R;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityImageEnlargeBinding;
import org.bihe.faranha.lowercaseimdb.databinding.ActivityMainBinding;

public class ImageEnlargeActivity extends AppCompatActivity {

    private ActivityImageEnlargeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        binding = ActivityImageEnlargeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ImageView enlargedImage = binding.enlargedImage;

        String imageUrl = getIntent().getStringExtra("IMAGE_URL");

        if (imageUrl != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_android_24)
                    .error(R.drawable.baseline_assignment_late_24)
                    .fitCenter()
                    .into(enlargedImage);
        }
    }
}