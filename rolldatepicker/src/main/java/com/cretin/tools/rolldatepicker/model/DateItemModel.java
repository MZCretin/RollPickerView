package com.cretin.tools.rolldatepicker.model;

import org.joda.time.DateTime;

/**
 * @date: on 2020-07-07
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加描述
 */
public class DateItemModel {
    private int isThisMonth;

    //是否被选中了
    private boolean isStartSelect;
    private boolean isEndSelect;

    private DateTime dateTime;

    private boolean isToday;

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public DateItemModel(int isThisMonth, DateTime dateTime) {
        this.isThisMonth = isThisMonth;
        this.dateTime = dateTime;
    }

    public int getIsThisMonth() {
        return isThisMonth;
    }

    public void setIsThisMonth(int isThisMonth) {
        this.isThisMonth = isThisMonth;
    }

    public boolean isStartSelect() {
        return isStartSelect;
    }

    public void setStartSelect(boolean startSelect) {
        isStartSelect = startSelect;
    }

    public boolean isEndSelect() {
        return isEndSelect;
    }

    public void setEndSelect(boolean endSelect) {
        isEndSelect = endSelect;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
