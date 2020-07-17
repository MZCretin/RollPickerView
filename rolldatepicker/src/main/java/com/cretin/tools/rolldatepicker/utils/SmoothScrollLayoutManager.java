package com.cretin.tools.rolldatepicker.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class SmoothScrollLayoutManager extends LinearLayoutManager {

        public SmoothScrollLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView,
                                           RecyclerView.State state, final int position) {

            LinearSmoothScroller smoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        // 返回：滑过1px时经历的时间(ms)。
                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 120f / displayMetrics.densityDpi;
                        }
                    };

            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }
    }