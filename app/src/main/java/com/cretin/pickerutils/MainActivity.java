package com.cretin.pickerutils;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import com.cretin.tools.rolldatepicker.PickerConfig;
import com.cretin.tools.rolldatepicker.listener.SelectCompleteListener;
import com.cretin.tools.rolldatepicker.view.DatePickerDialog;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PickerConfig.Builder build = PickerConfig.create()
                //按钮不可用背景
                .setBtnEnableBg(R.drawable.shape_ff7241_round_20_a30)
                //按钮正常背景
                .setBtnNormalBg(R.drawable.shape_ff7241_round_20)
                //设置按钮的文字
                .setConfirmBtnText(R.string.comfirm_text)
                //今日的日期颜色
                .setCurrentDayTextColor(Color.parseColor("#FF7241"))
                //date不可用颜色
                .setDateEnableTextColor(Color.parseColor("#cccccc"))
                //date正常颜色
                .setDateNormalTextColor(Color.parseColor("#333333"))
                //选择结束时间提示信息
                .setEndTimeTips(R.string.date_picker_select_end_time)
                //选择开始时间提示信息
                .setStartTimeTips(R.string.date_picker_select_start_time)
                //已选择日期的背景
                .setSelectedItemBg(R.drawable.shape_ff7241_round_100)
                //时间格式化
                .setTimeFormater(R.string.date_picker_time_format)
                .build();

        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, build, new SelectCompleteListener() {
                    @Override
                    public void onSelectComplete(boolean isCancel, DateTime startTime, DateTime endTime) {
                        if (isCancel) {
                            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(MainActivity.this, startTime.toString("yyyy-MM-dd") + " - " + endTime.toString("yyyy-MM-dd"), Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

    }
}
