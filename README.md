# **RollPickerView**

## 可选择开始日期和结束日期的日历样式的日期选择器

[![](https://jitpack.io/v/MZCretin/RollPickerView.svg)](https://jitpack.io/#MZCretin/RollPickerView)

### 大道至简系列
[需求解决系列之-【系列工具概览】](https://juejin.im/post/5ed6174f51882542fb06d850)
此系列是大道至简的起始，将一系列简单恶心的操作封装起来，框架么，可以败絮其中，但一定要金絮其外！

在工作之余，打算将一些常用的逻辑页面，模块，功能点做成library库，这样当有相似需求的时候，可以做到插拔式开发！现在系列中有以下内容

+ [App内部自动更新-AutoUpdateProject](https://github.com/MZCretin/AutoUpdateProject)
+ [选择城市-CitySelect](https://github.com/MZCretin/CitySelect)
+ [扫描二维码条形码控件-ScanCode](https://github.com/MZCretin/ScanCode)
+ [一键打开WebView件-WebViewUtils](https://github.com/MZCretin/WebViewUtils)
+ [简约动态权限申请库-FanPermission](https://github.com/MZCretin/FanPermission)
+ [弹出自定义支付密码输入框-InputPswDemo](https://github.com/MZCretin/InputPswDemo)
+ [安卓常用工具集成-AndroidUtils](https://github.com/MZCretin/AndroidUtilsProject)
+ [日历样式选择日期-RollPickerView](https://github.com/MZCretin/RollPickerView)

### 优势

+ **颜色文案可以自定义，默认支持国际化**
+ **秒打开，动画切换**
+ **可选择时间段，可设置默认选择时间**

### demo下载

[Demo下载](https://raw.githubusercontent.com/MZCretin/RollPickerView/master/pic/demo.apk)

扫描二维码下载：

<img src="./pic/erweima.png"/>

### 效果预览

<div style="background:#e3e3e3; color:#FFF" align=center ><img width="250" height="500" src="./pic/111.jpg?t=0"/><img width="250" height="500" src="./pic/222.jpg?t=0"/></div>

### 使用方式

+ Step1 Add it in your root build.gradle at the end of repositories.

  ```java
  allprojects {
  	repositories {
  		...
  		maven { url 'https://jitpack.io' }
  	}
  }
  ```

+ Step2 Add the dependency.

  ```java
  dependencies {
      implementation 'com.github.MZCretin:RollPickerView:v1.0.0'
  }
  ```

+ Step3 Open webview activity wherever you want.
  ```java
  
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
                //默认选择的开始时间戳
                .setStartTimestamp(DateTime.now().minusMonths(1).getMillis())
                //默认选择的结束时间戳
                .setEndTimestamp(DateTime.now().getMillis())
                //往前扩展的年份数
                .setPreYear(2)
                //往后扩展的年份数
                .setAfterYear(2)
                .build();

        findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, build, new SelectCompleteListener() {
                    @Override
                    public void onSelectComplete(boolean isCancel, DateTime startTime, DateTime endTime) {
                        if (isCancel) {
                            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ((TextView) findViewById(R.id.tv_info)).setText("您选择的日期段为：\n"+startTime.toString("yyyy-MM-dd") + " - " + endTime.toString("yyyy-MM-dd"));
                    }
                }).show();
            }
        });
  ```
