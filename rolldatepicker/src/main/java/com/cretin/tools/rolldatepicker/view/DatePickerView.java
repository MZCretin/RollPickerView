package com.cretin.tools.rolldatepicker.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cretin.tools.rolldatepicker.PickerConfig;
import com.cretin.tools.rolldatepicker.R;
import com.cretin.tools.rolldatepicker.adapter.RootRecyclerViewAdapter;
import com.cretin.tools.rolldatepicker.listener.SelectCompleteListener;
import com.cretin.tools.rolldatepicker.model.DateItemModel;
import com.cretin.tools.rolldatepicker.model.DateModel;
import com.cretin.tools.rolldatepicker.utils.SmoothScrollLayoutManager;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date: on 2020-07-07
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 自定义日期选择器
 */
public class DatePickerView extends ConstraintLayout {
    private RecyclerView recyclerView;
    private TextView tvStart;
    private TextView tvEnd;
    private TextView tvOk;
    private RootRecyclerViewAdapter mainAdapter;
    private Context context;
    private SelectCompleteListener selectCompleteListener;

    private List<DateModel> dateModels;

    private PickerConfig.Builder builder;

    public DatePickerView(Context context) {
        this(context, null, 0);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private MyHandler handler = null;

    static class MyHandler extends Handler {
        WeakReference weakReference;

        public MyHandler(DatePickerView activity) {
            weakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DatePickerView datePickerView = (DatePickerView) weakReference.get();
            Map<String, Object> t = (Map<String, Object>) msg.obj;
            DateTime endTime = (DateTime) t.get("endTime");
            DateTime originStartTime = (DateTime) t.get("originStartTime");
            DateTime now = (DateTime) t.get("now");
            DateTime beforeOneMonth = (DateTime) t.get("beforeOneMonth");

            datePickerView.initAdapter(beforeOneMonth, endTime, originStartTime, now);
        }
    }

    public DatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handler = new MyHandler(this);
        init(context, attrs, defStyleAttr);

        //我担心别人不初始化
        JodaTimeAndroid.init(context);
    }

    public SelectCompleteListener getSelectCompleteListener() {
        return selectCompleteListener;
    }

    public void setSelectCompleteListener(SelectCompleteListener selectCompleteListener) {
        this.selectCompleteListener = selectCompleteListener;
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        View.inflate(context, R.layout.layout_date_picker_view, this);

        initView();
    }

    /**
     * 绑定时间区间
     */
    public void bindTimeRange(PickerConfig.Builder builder, long startDate, long endDate) {
        if (builder != null) {
            this.builder = builder;
        } else {
            this.builder = PickerConfig.create().build();
        }

        tvOk.setText(builder.getConfirmBtnText());

        DateTime startTime = null;
        DateTime endTime = null;
        if ((startDate + "").length() == 10) {
            startTime = new DateTime(startDate * 1000);
        } else if ((startDate + "").length() == 13) {
            startTime = new DateTime(startDate);
        } else {
            throw new RuntimeException("startDate is illegal");
        }
        if ((endDate + "").length() == 10) {
            endTime = new DateTime(endDate * 1000);
        } else if ((startDate + "").length() == 13) {
            endTime = new DateTime(endDate);
        } else {
            throw new RuntimeException("endDate is illegal");
        }
        if (startDate > endDate) {
            throw new RuntimeException("startDate can not bigger than endDate");
        }
        initAdapterData(startTime, endTime);
    }

    /**
     * 处理时间
     *
     * @param sTime
     * @param eTime
     */
    private void initAdapterData(final DateTime sTime, final DateTime eTime) {
        PickerConfig.startTime = sTime;
        PickerConfig.endTime = eTime;

        new Thread(new Runnable() {
            @Override
            public void run() {
                DateTime originStartTime = sTime.minusYears(2);
                DateTime endTime = eTime.plusYears(2);

                DateTime now = eTime;
                DateTime beforeOneMonth = sTime;

                //计算两个时间之间有多少个月份 决定这list的长度
                DateTime startTime = new DateTime(originStartTime);
                int month = getMonthNumBetween(startTime, endTime);

                dateModels = new ArrayList<>();
                for (int i = 0; i < month; i++) {
                    DateModel temp = new DateModel();
                    temp.setYear(startTime.getYear());
                    temp.setMonth(startTime.getMonthOfYear());

                    //计算下面的具体时间
                    List<DateItemModel> times = new ArrayList<>();

                    //获取本月第一个跟最后一天
                    DateTime monthStart = startTime.dayOfMonth().withMinimumValue();
                    DateTime monthEnd = startTime.dayOfMonth().withMaximumValue();
                    int weekStart = monthStart.getDayOfWeek() % 7;

                    //需要获取的上个月的天数 先添加上个月的时间
                    if (weekStart != 0) {
                        DateTime lastMonthDateTime = monthStart.minusMonths(1);
                        DateTime lastMonthEnd = lastMonthDateTime.dayOfMonth().withMaximumValue();
                        for (int i1 = weekStart; i1 > 0; i1--) {
                            DateTime tempTime = lastMonthEnd.minusDays(i1 - 1);
                            times.add(new DateItemModel(-1, tempTime));
                        }
                    }

                    //添加本月的数据
                    for (int j = 1; j <= monthEnd.getDayOfMonth(); j++) {
                        DateTime tempTime = new DateTime(monthStart.getYear(), monthStart.getMonthOfYear(), j, 0, 0, 0);
                        DateItemModel itemModel = new DateItemModel(0, tempTime);
                        if (tempTime.getYear() == beforeOneMonth.getYear() && tempTime.getMonthOfYear() ==
                                beforeOneMonth.getMonthOfYear() && tempTime.getDayOfMonth() == beforeOneMonth.getDayOfMonth()) {
                            itemModel.setToday(false);
                            itemModel.setStartSelect(true);
                        }
                        if (tempTime.getYear() == now.getYear() && tempTime.getMonthOfYear() ==
                                now.getMonthOfYear() && tempTime.getDayOfMonth() == now.getDayOfMonth()) {
                            itemModel.setToday(true);
                            itemModel.setEndSelect(true);
                        }
                        times.add(itemModel);
                    }

                    //添加下个月的数据
                    if (monthEnd.getDayOfWeek() != 6) {
                        int leftDays = 0;
                        //有没有充满的数据在里面
                        if (monthEnd.getDayOfWeek() < 6) {
                            //最后一排还差几个
                            leftDays = 7 - monthEnd.getDayOfWeek() - 1;
                        } else {
                            //卧槽 最一排就只有一个
                            leftDays = 6;
                        }
                        DateTime nextMonth = monthStart.plusMonths(1);
                        for (int j = 0; j < leftDays; j++) {
                            times.add(new DateItemModel(1, nextMonth));
                            nextMonth = nextMonth.plusDays(1);
                        }
                    }

                    temp.setItems(times);
                    dateModels.add(temp);

                    startTime = startTime.plusMonths(1);

                    Message message = handler.obtainMessage();
                    Map<String, Object> params = new HashMap<>();
                    params.put("originStartTime", originStartTime);
                    params.put("endTime", endTime);
                    params.put("now", now);
                    params.put("beforeOneMonth", beforeOneMonth);
                    params.put("dateModels", dateModels);
                    message.obj = params;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        tvStart = findViewById(R.id.tv_start);
        tvEnd = findViewById(R.id.tv_end);
        tvOk = findViewById(R.id.tv_ok);

        tvStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerConfig.datePickerState = 0;
                getSelectTime();
            }
        });

        tvEnd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerConfig.datePickerState = 1;
                getSelectTime();
            }
        });

        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PickerConfig.startTime != null && PickerConfig.endTime != null) {
                    if (selectCompleteListener != null) {
                        selectCompleteListener.onSelectComplete(false, PickerConfig.startTime, PickerConfig.endTime);
                    }
                }
            }
        });
    }

    private void initAdapter(DateTime beforeOneMonth, DateTime endTime, DateTime originStartTime, DateTime now) {
        SmoothScrollLayoutManager ms = new SmoothScrollLayoutManager(context);
        recyclerView.setLayoutManager(ms);
        recyclerView.setNestedScrollingEnabled(false);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
        mainAdapter = new RootRecyclerViewAdapter(this, context, dateModels,builder);
        mainAdapter.bindRecyclerView(recyclerView);
        recyclerView.setAdapter(mainAdapter);

        //默认进来选择的是今日以及今日之前一个月的今天
        if (beforeOneMonth.isBefore(endTime) && beforeOneMonth.isAfter(originStartTime) && now.isBefore(endTime) && now.isAfter(originStartTime)) {
            //在这个区间范围
            int position = getMonthNumBetween(originStartTime, beforeOneMonth) - 1;
            recyclerView.scrollToPosition(position);
            mainAdapter.notifyItemChanged(position);

            tvStart.setText(beforeOneMonth.toString(getResources().getString(builder.getTimeFormater())));
            tvEnd.setText(now.toString(getResources().getString(builder.getTimeFormater())));

            tvStart.setTextColor(Color.parseColor("#333333"));
            tvEnd.setTextColor(Color.parseColor("#E6E6E6"));

            PickerConfig.startTime = beforeOneMonth;
            PickerConfig.endTime = now;

            tvOk.setBackgroundResource(builder.getBtnNormalBg());
            tvOk.setEnabled(true);
        }
    }

    private void getSelectTime() {
        //找开始时间和结束时间
        resetData();

        //计算对应的position 然后滚到到那里 刷新适配器
        int startPosition = -1;
        int endPosition = -1;
        if (PickerConfig.startTime != null) {
            startPosition = dateModels.indexOf(new DateModel(PickerConfig.startTime.getYear(), PickerConfig.startTime.getMonthOfYear()));
            mainAdapter.notifyItemChanged(startPosition);
            if (PickerConfig.datePickerState == 0) {
                //开始时间
                recyclerView.scrollToPosition(startPosition);
            }
        }
        if (PickerConfig.endTime != null) {
            endPosition = dateModels.indexOf(new DateModel(PickerConfig.endTime.getYear(), PickerConfig.endTime.getMonthOfYear()));
            mainAdapter.notifyItemChanged(endPosition);
            if (PickerConfig.datePickerState == 1) {
                //结束时间
                if (PickerConfig.endTime != null) {
                    recyclerView.scrollToPosition(endPosition);
                }
            }
        }
    }

    private void resetData() {
        if (PickerConfig.startTime != null && PickerConfig.endTime != null) {
            //两个都选择了
            tvOk.setBackgroundResource(R.drawable.shape_ff7241_round_20);
            tvOk.setEnabled(true);
        } else {
            tvOk.setBackgroundResource(R.drawable.shape_ff7241_round_20_a30);
            tvOk.setEnabled(false);
        }
        tvStart.setText(PickerConfig.startTime == null ? context.getString(builder.getStartTimeTips()) : PickerConfig.startTime.toString(getResources().getString(builder.getTimeFormater())));
        tvEnd.setText(PickerConfig.endTime == null ? context.getString(builder.getEndTimeTips()) : PickerConfig.endTime.toString(getResources().getString(builder.getTimeFormater())));
        if (PickerConfig.datePickerState == 0) {
            tvStart.setTextColor(Color.parseColor("#333333"));
            tvEnd.setTextColor(Color.parseColor("#E6E6E6"));
        } else {
            tvStart.setTextColor(Color.parseColor("#E6E6E6"));
            tvEnd.setTextColor(Color.parseColor("#333333"));
        }
    }

    /**
     * 获取月份
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private int getMonthNumBetween(DateTime startTime, DateTime endTime) {
        int startYear = startTime.getYear();
        int startMonth = startTime.getMonthOfYear();
        int endYear = endTime.getYear();
        int endMonth = endTime.getMonthOfYear();
        if (startYear == endYear) {
            //同一年的好说
            return endMonth - startMonth + 1;
        } else {
            //不同一年 那么久月份就是从这个月到年底 次年的年初到终止月份 + 中间隔的年份*12
            return (12 - startMonth + 1) + endMonth + (endYear - startYear - 1) * 12;
        }
    }

    public void selectedTime() {
        if (PickerConfig.datePickerState == 0) {
            tvEnd.performClick();
        }
        resetData();
    }
}
