package com.cretin.tools.rolldatepicker;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import org.joda.time.DateTime;

/**
 * @date: on 2020-07-08
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: Picker的配置类
 */
public class PickerConfig {

    private Builder builder;

    //0 选择开始时间 1 选择结束时间
    public static int datePickerState = 0;

    //开始时间
    public static DateTime startTime;

    private PickerConfig(Builder builder) {
        this.builder = builder;
    }

    //结束
    public static DateTime endTime;

    public static PickerConfig create() {
        return new PickerConfig(new Builder());
    }

    public PickerConfig setStartTimeTips(@StringRes int startTimeTips) {
        if (builder != null)
            builder.startTimeTips = startTimeTips;
        return this;
    }

    public PickerConfig setEndTimeTips(@StringRes int endTimeTips) {
        if (builder != null)
            builder.endTimeTips = endTimeTips;
        return this;
    }

    public PickerConfig setTimeFormater(@StringRes int timeFormater) {
        if (builder != null)
            builder.timeFormater = timeFormater;
        return this;
    }

    public PickerConfig setSelectedItemBg(@DrawableRes int selectedItemBg) {
        if (builder != null)
            builder.selectedItemBg = selectedItemBg;
        return this;
    }

    public PickerConfig setCurrentDayTextColor(@ColorInt int currentDayTextColor) {
        if (builder != null)
            builder.currentDayTextColor = currentDayTextColor;
        return this;
    }

    public PickerConfig setDateNormalTextColor(@ColorInt int dateNormalTextColor) {
        if (builder != null)
            builder.dateNormalTextColor = dateNormalTextColor;
        return this;
    }

    public PickerConfig setDateEnableTextColor(@ColorInt int dateEnableTextColor) {
        if (builder != null)
            builder.dateEnableTextColor = dateEnableTextColor;
        return this;
    }

    public PickerConfig setBtnNormalBg(@DrawableRes int btnNormalBg) {
        if (builder != null)
            builder.btnNormalBg = btnNormalBg;
        return this;
    }

    public PickerConfig setBtnEnableBg(@DrawableRes int btnEnableBg) {
        if (builder != null)
            builder.btnEnableBg = btnEnableBg;
        return this;
    }

    public PickerConfig setConfirmBtnText(@StringRes int confirmBtnText) {
        if (builder != null)
            builder.confirmBtnText = confirmBtnText;
        return this;
    }

    public PickerConfig setStartTimestamp(long startTimestamp) {
        if (builder != null)
            builder.startTimestamp = startTimestamp;
        return this;
    }

    public PickerConfig setEndTimestamp(long endTimestamp) {
        if (builder != null)
            builder.endTimestamp = endTimestamp;
        return this;
    }

    public PickerConfig setPreYear(int preYear) {
        if (builder != null)
            builder.preYear = preYear;
        return this;
    }

    public PickerConfig setAfterYear(int afterYear) {
        if (builder != null)
            builder.afterYear = afterYear;
        return this;
    }

    public Builder build() {
        return builder;
    }

    public static class Builder {
        //选择开始时间提示信息
        private @StringRes
        int startTimeTips = R.string.date_picker_select_start_time;
        //选择结束时间提示信息
        private @StringRes
        int endTimeTips = R.string.date_picker_select_end_time;
        //时间格式化
        private @StringRes
        int timeFormater = R.string.date_picker_time_format;
        //确定按钮的文案
        private @StringRes
        int confirmBtnText = R.string.comfirm_text;
        //已选择日期的背景
        private @DrawableRes
        int selectedItemBg = R.drawable.shape_ff7241_round_100;
        //今日的日期颜色
        private @ColorInt
        int currentDayTextColor = Color.parseColor("#FF7241");
        //date正常颜色
        private @ColorInt
        int dateNormalTextColor = Color.parseColor("#333333");
        //date不可用颜色
        private @ColorInt
        int dateEnableTextColor = Color.parseColor("#cccccc");
        //按钮正常背景
        private @DrawableRes
        int btnNormalBg = R.drawable.shape_ff7241_round_20;
        //按钮不可用背景
        private @DrawableRes
        int btnEnableBg = R.drawable.shape_ff7241_round_20_a30;
        //默认选择的开始时间戳
        private long startTimestamp = DateTime.now().minusMonths(1).getMillis();
        //默认选择的结束时间戳
        private long endTimestamp = DateTime.now().getMillis();
        //往前扩展的年份数
        private int preYear = 2;
        //往后扩展的年份数
        private int afterYear = 2;

        public long getStartTimestamp() {
            return startTimestamp;
        }

        public long getEndTimestamp() {
            return endTimestamp;
        }

        public int getPreYear() {
            return preYear;
        }

        public int getAfterYear() {
            return afterYear;
        }

        private Builder() {
        }

        public @StringRes
        int getStartTimeTips() {
            return startTimeTips;
        }

        public @StringRes
        int getEndTimeTips() {
            return endTimeTips;
        }

        public @StringRes
        int getTimeFormater() {
            return timeFormater;
        }

        public int getConfirmBtnText() {
            return confirmBtnText;
        }

        public @DrawableRes
        int getSelectedItemBg() {
            return selectedItemBg;
        }

        public @ColorInt
        int getCurrentDayTextColor() {
            return currentDayTextColor;
        }

        public @ColorInt
        int getDateNormalTextColor() {
            return dateNormalTextColor;
        }

        public @ColorInt
        int getDateEnableTextColor() {
            return dateEnableTextColor;
        }

        public @DrawableRes
        int getBtnNormalBg() {
            return btnNormalBg;
        }

        public @DrawableRes
        int getBtnEnableBg() {
            return btnEnableBg;
        }
    }
}
