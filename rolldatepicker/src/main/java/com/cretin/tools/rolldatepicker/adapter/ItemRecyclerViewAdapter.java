package com.cretin.tools.rolldatepicker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.cretin.tools.rolldatepicker.PickerConfig;
import com.cretin.tools.rolldatepicker.R;
import com.cretin.tools.rolldatepicker.model.DateItemModel;
import com.cretin.tools.rolldatepicker.model.DateModel;
import com.cretin.tools.rolldatepicker.utils.DensityUtil;

import org.joda.time.DateTime;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.RecyclerHolder> {
    private Context mContext;
    private List<DateItemModel> dataList;
    private int itemHeight;
    private int viewItemHeight;
    private RootRecyclerViewAdapter rootRecyclerViewAdapter;
    private List<DateModel> rootList;
    private int rootPosition;
    private PickerConfig.Builder builder;

    public ItemRecyclerViewAdapter(Context context, List<DateItemModel> dataList, RootRecyclerViewAdapter rootRecyclerViewAdapter, List<DateModel> list, int position, PickerConfig.Builder builder) {
        this.mContext = context;
        this.rootRecyclerViewAdapter = rootRecyclerViewAdapter;
        this.rootList = list;
        this.rootPosition = position;
        this.builder = builder;

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        itemHeight = (int) (width * 1.0f * 538 / 750 / (dataList.size() > 35 ? 6 : 5));
        viewItemHeight = (int) (width * 1.0f * 538 / 750 / 6) - DensityUtil.dp2px(6);

        this.dataList = dataList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_item_view, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        final DateItemModel itemModel = dataList.get(position);

        if (itemModel.getIsThisMonth() == 0) {
            if (PickerConfig.datePickerState == 0) {
                if (itemModel.isStartSelect()) {
                    holder.textView.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    if (itemModel.isToday()) {
                        holder.textView.setTextColor(builder.getCurrentDayTextColor());
                    } else {
                        holder.textView.setTextColor(builder.getDateNormalTextColor());
                    }
                }
            } else {
                if (itemModel.isEndSelect()) {
                    holder.textView.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    if (itemModel.isToday()) {
                        holder.textView.setTextColor(builder.getCurrentDayTextColor());
                    } else {
                        holder.textView.setTextColor(builder.getDateNormalTextColor());
                    }
                }
            }
        } else {
            holder.textView.setTextColor(builder.getDateEnableTextColor());
        }

        if (itemModel.getDateTime().getDayOfMonth() == 1) {
            holder.textView.setText(itemModel.getDateTime().toString("MMM"));
        } else {
            holder.textView.setText(itemModel.getDateTime().getDayOfMonth() + "");
        }

        if (PickerConfig.datePickerState == 0) {
            if (itemModel.isStartSelect()) {
                holder.textView.setBackground(mContext.getResources().getDrawable(builder.getSelectedItemBg()));
            } else {
                holder.textView.setBackground(null);
            }
        } else {
            if (itemModel.isEndSelect()) {
                holder.textView.setBackground(mContext.getResources().getDrawable(builder.getSelectedItemBg()));
            } else {
                holder.textView.setBackground(null);
            }
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重置所有数据
                rootRecyclerViewAdapter.resetData();

                if (itemModel.getIsThisMonth() == 0) {
                    //这个月
                    if (PickerConfig.datePickerState == 0) {
                        itemModel.setStartSelect(true);
                    } else {
                        itemModel.setEndSelect(true);
                    }
                    notifyDataSetChanged();
                } else {
                    if (itemModel.getIsThisMonth() == -1) {
                        //上个月
                        if (rootPosition > 0) {
                            DateModel dateModel = rootList.get(rootPosition - 1);
                            for (DateItemModel item : dateModel.getItems()) {
                                if (item.getDateTime().getYear() == itemModel.getDateTime().getYear() &&
                                        item.getDateTime().getMonthOfYear() == itemModel.getDateTime().getMonthOfYear() &&
                                        item.getDateTime().getDayOfMonth() == itemModel.getDateTime().getDayOfMonth()) {
                                    if (PickerConfig.datePickerState == 0) {
                                        item.setStartSelect(true);
                                    } else {
                                        item.setEndSelect(true);
                                    }
                                } else {
                                    if (PickerConfig.datePickerState == 0) {
                                        item.setStartSelect(false);
                                    } else {
                                        item.setEndSelect(false);
                                    }
                                }
                            }
                            //滚到上一页
                            rootRecyclerViewAdapter.getRecyclerView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rootRecyclerViewAdapter.getRecyclerView().smoothScrollToPosition(rootPosition - 1);
                                    rootRecyclerViewAdapter.notifyItemChanged(rootPosition - 1);
                                }
                            }, 100);
                        } else {
                            if (PickerConfig.datePickerState == 0) {
                                itemModel.setStartSelect(true);
                            } else {
                                itemModel.setEndSelect(true);
                            }
                            notifyDataSetChanged();
                        }
                    } else {
                        //下个月
                        if (rootPosition < rootList.size() - 1) {
                            DateModel dateModel = rootList.get(rootPosition + 1);
                            for (DateItemModel item : dateModel.getItems()) {
                                if (item.getDateTime().getYear() == itemModel.getDateTime().getYear() &&
                                        item.getDateTime().getMonthOfYear() == itemModel.getDateTime().getMonthOfYear() &&
                                        item.getDateTime().getDayOfMonth() == itemModel.getDateTime().getDayOfMonth()) {
                                    if (PickerConfig.datePickerState == 0) {
                                        item.setStartSelect(true);
                                    } else {
                                        item.setEndSelect(true);
                                    }
                                } else {
                                    if (PickerConfig.datePickerState == 0) {
                                        item.setStartSelect(false);
                                    } else {
                                        item.setEndSelect(false);
                                    }
                                }
                            }
                            //滚到上一页
                            rootRecyclerViewAdapter.getRecyclerView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rootRecyclerViewAdapter.getRecyclerView().smoothScrollToPosition(rootPosition + 1);
                                    rootRecyclerViewAdapter.notifyItemChanged(rootPosition + 1);
                                }
                            }, 100);
                        } else {
                            if (PickerConfig.datePickerState == 0) {
                                itemModel.setStartSelect(true);
                            } else {
                                itemModel.setEndSelect(true);
                            }
                            notifyDataSetChanged();
                        }
                    }
                }

                //这个月
                if (PickerConfig.datePickerState == 0) {
                    PickerConfig.startTime = itemModel.getDateTime();
                } else {
                    PickerConfig.endTime = itemModel.getDateTime();
                }

                //如果选择的时间结束时间比开始时间要早 或者 开始时间比结束视角完 要置空一个时间
                if (PickerConfig.startTime != null && PickerConfig.endTime != null) {
                    if (PickerConfig.startTime.isAfter(PickerConfig.endTime)) {
                        //时间不对
                        if (PickerConfig.datePickerState == 0) {
                            PickerConfig.endTime = null;
                            rootRecyclerViewAdapter.clearEndTime();
                        } else {
                            PickerConfig.startTime = null;
                            rootRecyclerViewAdapter.clearStartTime();
                        }
                    }
                }

                rootRecyclerViewAdapter.notifyDataSetChanged();
                rootRecyclerViewAdapter.selectedTime();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ConstraintLayout rootView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.root_view);
            textView = (TextView) itemView.findViewById(R.id.tv_date);

            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = itemHeight;
            rootView.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams layoutParams1 = textView.getLayoutParams();
            layoutParams1.width = viewItemHeight;
            layoutParams1.height = viewItemHeight;
            textView.setLayoutParams(layoutParams1);
        }
    }
}