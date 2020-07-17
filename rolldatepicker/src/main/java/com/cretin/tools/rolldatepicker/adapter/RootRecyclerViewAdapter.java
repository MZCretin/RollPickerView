package com.cretin.tools.rolldatepicker.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cretin.tools.rolldatepicker.PickerConfig;
import com.cretin.tools.rolldatepicker.R;
import com.cretin.tools.rolldatepicker.model.DateItemModel;
import com.cretin.tools.rolldatepicker.model.DateModel;
import com.cretin.tools.rolldatepicker.view.DatePickerView;

import java.util.List;

public class RootRecyclerViewAdapter extends RecyclerView.Adapter<RootRecyclerViewAdapter.RecyclerHolder> {
    private Context mContext;
    private List<DateModel> dataList;
    private RecyclerView recyclerView;
    private DatePickerView datePickerView;
    private PickerConfig.Builder builder;

    public RootRecyclerViewAdapter(DatePickerView datePickerView, Context context, List<DateModel> dataList, PickerConfig.Builder builder) {
        this.mContext = context;
        this.dataList = dataList;
        this.datePickerView = datePickerView;
        this.builder = builder;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_main_page, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        ItemRecyclerViewAdapter itemAdapter = new ItemRecyclerViewAdapter(mContext, dataList.get(position).getItems(), this, dataList, position,builder);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        holder.recyclerView.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void resetData() {
        for (DateModel dateModel : dataList) {
            for (DateItemModel item : dateModel.getItems()) {
                if (PickerConfig.datePickerState == 0) {
                    item.setStartSelect(false);
                } else {
                    item.setEndSelect(false);
                }
            }
        }
    }

    public void selectedTime() {
        datePickerView.selectedTime();
    }

    public void clearStartTime() {
        for (DateModel dateModel : dataList) {
            for (DateItemModel item : dateModel.getItems()) {
                item.setStartSelect(false);
            }
        }
    }

    public void clearEndTime() {
        for (DateModel dateModel : dataList) {
            for (DateItemModel item : dateModel.getItems()) {
                item.setEndSelect(false);
            }
        }
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }

    public void bindRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}