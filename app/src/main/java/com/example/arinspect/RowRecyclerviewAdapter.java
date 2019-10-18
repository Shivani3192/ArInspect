package com.example.arinspect;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arinspect.database.entity.Row;
import com.example.arinspect.databinding.AdapterRowItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RowRecyclerviewAdapter extends RecyclerView.Adapter<RowRecyclerviewAdapter.ViewHolder> {
    private List<Row> mRowList = new ArrayList<>();

    RowRecyclerviewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdapterRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mAdapterRowItemBinding.setRow(mRowList.get(position));
        if (position == mRowList.size() - 1) {
            holder.setLastItem(true);
        } else {
            holder.setLastItem(false);
        }
    }

    @Override
    public int getItemCount() {
        return mRowList != null ? mRowList.size() : 0;
    }

    void setRowList(List<Row> rowList) {
        mRowList = rowList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterRowItemBinding mAdapterRowItemBinding;

        public boolean isLastItem() {
            return isLastItem;
        }

        public void setLastItem(boolean lastItem) {
            isLastItem = lastItem;
        }

        boolean isLastItem;

        ViewHolder(AdapterRowItemBinding adapterRowItemBinding) {
            super(adapterRowItemBinding.getRoot());
            mAdapterRowItemBinding = adapterRowItemBinding;
        }
    }
}