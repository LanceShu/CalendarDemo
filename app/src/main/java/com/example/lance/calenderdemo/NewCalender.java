package com.example.lance.calenderdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lance on 2017/10/9.
 */

public class NewCalender extends LinearLayout {

    //上一个月;
    private ImageView pre;
    //下一个月;
    private ImageView next;
    //显示当前年月;
    private TextView calText;
    //用于显示日期;
    private GridView gridView;

    //当前的日期;
    private Calendar curDate = Calendar.getInstance();
    //年月的显示的格式;
    private String displayFormat;

    //长按监听事件;
    public NewCalendarListener listener;

    public NewCalender(Context context) {
        super(context);
    }

    public NewCalender(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initControl(context,attrs);
    }

    public NewCalender(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initControl(context,attrs);
    }

    //初始化数据;
    private void initControl(Context context,AttributeSet attrs){
        bindControl(context);
        bindControlEvent();

        //获取Attribute属性;
        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.NewCalender);

        try{
            String formate = ta.getString(R.styleable.NewCalender_dateFormat);
            displayFormat = formate;
            if(displayFormat == null){
                displayFormat = "MMM yyyy";
            }
        }finally {
            ta.recycle();
        }
        renderCalendar();
    }

    //绑定控件;
    private void bindControl(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calender_view,this);

        pre = (ImageView) findViewById(R.id.pre_month);
        next = (ImageView) findViewById(R.id.next_month);
        calText = (TextView) findViewById(R.id.calender_text);
        gridView = (GridView) findViewById(R.id.calender_grid);
    }

    //绑定控件的事件;
    private void bindControlEvent() {
        pre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //当前月份减去1，得到上个月;
                curDate.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //当前月份加1，得到下个月;
                curDate.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }

    private void renderCalendar(){

        //简单数据格式格式化;
        SimpleDateFormat sdf = new SimpleDateFormat(displayFormat);
        calText.setText(sdf.format(curDate.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) curDate.clone();

        //设置当前日期到该月的第一天;
        calendar.set(Calendar.DAY_OF_MONTH,1);
        //获得当前的星期几;
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);

        int maxCellCount = 6 * 7;
        while(cells.size() < maxCellCount){
            cells.add(calendar.getTime());
            //日期加1,获得明天的日期;
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        gridView.setAdapter(new CalendarAdapter(getContext(),cells));

        //gridView单个item长按的监听事件;
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(listener == null){
                    return false;
                }else{
                    listener.onItemLongPress((Date) adapterView.getItemAtPosition(i));
                    return true;
                }
            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        LayoutInflater inflater;

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> dates) {
            super(context, R.layout.calendar_text_day,dates);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Date date = getItem(position);

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_text_day, parent, false);
            }

            int day = date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));

            Date now = new Date();
            boolean isTheSameMoth = false;
            if(date.getMonth() == now.getMonth()){
                isTheSameMoth = true;
            }

            if(isTheSameMoth){
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else{
                ((TextView)convertView).setTextColor(Color.parseColor("#666666"));
            }

            if(now.getDate() == date.getDate() && now.getMonth() == date.getMonth()
                    && now.getYear() == date.getYear()){
                ((TextView)convertView).setTextColor(Color.parseColor("#ff0000"));
                ((Calendar_day_TextView)convertView).isToday = true;
            }

            return convertView;
        }
    }

    //长按接口;
    public interface NewCalendarListener{
        void onItemLongPress(Date date);
    }
}
