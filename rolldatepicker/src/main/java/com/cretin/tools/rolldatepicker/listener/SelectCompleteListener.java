package com.cretin.tools.rolldatepicker.listener;

import org.joda.time.DateTime;

/**
 * @date: on 2020-07-08
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加描述
 */
public interface SelectCompleteListener {
    void onSelectComplete(boolean isCancel,DateTime startTime, DateTime endTime);
}
