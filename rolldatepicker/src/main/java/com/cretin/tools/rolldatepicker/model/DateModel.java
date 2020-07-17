package com.cretin.tools.rolldatepicker.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @date: on 2020-07-07
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加描述
 */
public class DateModel {
    private int year;
    private int month;

    public DateModel() {
    }

    public DateModel(int year, int month) {
        this.year = year;
        this.month = month;
    }

    //这个列表里面可能有35条数据有可能有42条数据
    private List<DateItemModel> items;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<DateItemModel> getItems() {
        return items;
    }

    public void setItems(List<DateItemModel> items) {
        this.items = items;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        DateModel o = (DateModel) obj;
        return o.getMonth() == getMonth() && o.getYear() == getYear();
    }
}
