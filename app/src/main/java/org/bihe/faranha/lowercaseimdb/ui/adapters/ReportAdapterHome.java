package org.bihe.faranha.lowercaseimdb.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.databinding.ItemReportHomeBinding;

import java.util.List;

public class ReportAdapterHome extends RecyclerView.Adapter<ReportAdapterHome.ViewHolder> {

    private List<Report> reports;
    ReportAdapterHomeCallback callback;
    private Context context;
    private LayoutInflater layoutInflater;

    public ReportAdapterHome(List<Report> reports, Context context,ReportAdapterHomeCallback callback) {
        this.reports = reports;
        this.context = context;
        this.callback = callback;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public ReportAdapterHome(List<Report> reports, Context context) {
        this.reports = reports;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReportHomeBinding binding = ItemReportHomeBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        private ItemReportHomeBinding binding;
        private int position;

        public ViewHolder(@NonNull ItemReportHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(int position) {
            this.position = position;
            Report report = reports.get(position);
            binding.titleTvHome.setText(report.getTitle());
            binding.contentTvHome.setText(report.getBody());
            binding.reportLayHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onReportClicked(report);
                }
            });
        }
    }

    public interface ReportAdapterHomeCallback {
        void onReportClicked(Report report);
    }
}
