package com.cretin.tools.rolldatepicker.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.cretin.tools.rolldatepicker.PickerConfig;
import com.cretin.tools.rolldatepicker.R;
import com.cretin.tools.rolldatepicker.listener.SelectCompleteListener;

import org.joda.time.DateTime;


public class DatePickerDialog extends Dialog {

    private Context context;

    private SelectCompleteListener listener;

    private boolean hasShowInfo = false;

    private PickerConfig.Builder builder;

    private OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if (!hasShowInfo && listener != null) {
                listener.onSelectComplete(true, null, null);
            }
        }
    };

    public DatePickerDialog(@NonNull Context context, PickerConfig.Builder builder, SelectCompleteListener listener) {
        super(context, R.style.BottomDialog);//第二个参数是不设置的话dialog将无法占满设置的大小，有默认边距
        this.context = context;
        this.listener = listener;
        this.builder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatePickerView rootView = new DatePickerView(context);

        rootView.bindTimeRange(builder, builder.getStartTimestamp()
                , builder.getEndTimestamp());
        setContentView(rootView); //设置dialog布局

        rootView.setSelectCompleteListener(new SelectCompleteListener() {
            @Override
            public void onSelectComplete(boolean isCancel, DateTime startTime, DateTime endTime) {
                if (listener != null) {
                    listener.onSelectComplete(false, startTime, endTime);
                }
                hasShowInfo = true;
                dismiss();
            }
        });

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;//设置布局属性占满宽度
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//设置布局属性适应高度
        params.gravity = Gravity.BOTTOM;//设置dialog位于底部
        params.windowAnimations = R.style.mypopwindow_anim_style;//设置dialog进入和出去的动画
        window.setAttributes(params);

        setCanceledOnTouchOutside(true);

        setCancelable(true);//设置点击dialog外部无效，不关闭dialog

        setOnDismissListener(dismissListener);
    }
}
