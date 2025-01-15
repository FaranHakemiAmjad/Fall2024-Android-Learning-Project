package org.bihe.faranha.lowercaseimdb.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.bihe.faranha.lowercaseimdb.data.file.PreferencesManager;
import org.bihe.faranha.lowercaseimdb.R;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;
import org.bihe.faranha.lowercaseimdb.databinding.ItemReportProfileBinding;
import org.bihe.faranha.lowercaseimdb.ui.activities.ImageEnlargeActivity;

import com.bumptech.glide.Glide;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{

    private List<Report> reports;
    ReportAdapterCallback callback;
    private Context context;
    private LayoutInflater layoutInflater;

    public ReportAdapter(List<Report> reports, Context context, ReportAdapterCallback callback) {
        this.reports = reports;
        this.context = context;
        this.callback = callback;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public ReportAdapter(List<Report> reports, Context context) {
        this.reports = reports;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReportProfileBinding binding = ItemReportProfileBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void remove(int removePosition) {
        reports.remove(removePosition);
        notifyItemRemoved(removePosition);
        notifyItemRangeChanged(removePosition, getItemCount());
    }

    public void remove(long id) {
        for (int i = 0; i < reports.size(); i++) {
            if (id == reports.get(i).getId()) {
                reports.remove(i);
                notifyItemRangeChanged(i, reports.size());
                return;
            }
        }
    }

    public void add(Report report) {
        reports.add(report);
        int insertedPosition = report.getId() - 1;
        notifyItemInserted(insertedPosition);
        notifyItemRangeChanged(insertedPosition, getItemCount());
    }

    public void updateAll(List<Report> reports) {
        this.reports = reports;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemReportProfileBinding binding;
        private int position;

        public ViewHolder(@NonNull ItemReportProfileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(int position) {
            this.position = position;
            Report report = reports.get(position);
            if (report.getImageUrl() != null) {
                Glide.with(binding.reportImage.getContext())
                        .load(report.getImageUrl().toString())
                        .placeholder(R.drawable.baseline_android_24)
                        .error(R.drawable.baseline_assignment_late_24)
                        .centerCrop()
                        .into(binding.reportImage);
            } else {
                binding.reportImage.setImageResource(R.drawable.baseline_android_24);
            }
//            PreferencesManager preferencesManager = PreferencesManager.getInstance(context.getApplicationContext());
//            String storedName = preferencesManager.get(PreferencesManager.PREF_KEY_NAME, "");

            ReportCategory category = report.getCategory();
            Button categoryIndicator = binding.categoryIndicatorBtn;
            switch (category) {
                case NEWS:
                    categoryIndicator.setBackground(ContextCompat.getDrawable(categoryIndicator.getContext(), R.drawable.news_button_shape));
                    categoryIndicator.setText(String.valueOf(category));
                    break;
                case SHOWS:
                    categoryIndicator.setBackground(ContextCompat.getDrawable(categoryIndicator.getContext(),R.drawable.shows_button_shape));
                    categoryIndicator.setText(String.valueOf(category));
                    break;
                case CELEBS:
                    categoryIndicator.setBackground(ContextCompat.getDrawable(categoryIndicator.getContext(),R.drawable.celebs_button_shape));
                    categoryIndicator.setText(String.valueOf(category));
                    break;
                case EVENTS:
                    categoryIndicator.setBackground(ContextCompat.getDrawable(categoryIndicator.getContext(),R.drawable.events_button_shape));
                    categoryIndicator.setText(String.valueOf(category));
                    break;
                case MOVIES:
                    categoryIndicator.setBackground(ContextCompat.getDrawable(categoryIndicator.getContext(),R.drawable.movies_button_shape));
                    categoryIndicator.setText(String.valueOf(category));
                    break;
            }
            binding.titleTv.setText(report.getTitle());
            binding.contentTv.setText(report.getBody());
            binding.dateTv.setText(report.getDate().toString());
            binding.senderTv.setText(report.getSender());
            binding.reportLayProfile.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callback.onReportLongClicked(position);
                    return true;
                }
            });

            binding.reportImage.setOnClickListener(v -> {
                Intent intent = new Intent(context, ImageEnlargeActivity.class);
                if (report.getImageUrl() != null) {
                    intent.putExtra("IMAGE_URL", report.getImageUrl().toString());
                }

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) context, binding.reportImage, "shared_image"
                );
                context.startActivity(intent, options.toBundle());
            });

        }
    }
        public interface ReportAdapterCallback {
            void onReportLongClicked(int position);
        }
}
