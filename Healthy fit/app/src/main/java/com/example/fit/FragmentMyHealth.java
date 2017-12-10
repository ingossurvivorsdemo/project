package com.example.fit;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echo.holographlibrary.Bar;

import java.util.ArrayList;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class FragmentMyHealth extends Fragment implements  View.OnClickListener {

    TextView tv_quantity;
    TextView tv_quality;
    TextView tv_recom;

    int index = 1;

    int quantiry_sleep;
    int quality_sleep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_myhealth, container, false);

        tv_quantity = (TextView) rootView.findViewById(R.id.textView_quantity);
        tv_quality = (TextView) rootView.findViewById(R.id.textView_quality);

        tv_recom = (TextView) rootView.findViewById(R.id.textView_recom);
        tv_recom.setOnClickListener(this);

        //tv_quantity.setText(String.valueOf(quantiry_sleep));
        //tv_quality.setText(String.valueOf(quality_sleep));


        ArrayList<Bar> points = new ArrayList<Bar>();

        Bar d1 = new Bar();
        d1.setColor(Color.parseColor("#99CC00"));
        d1.setValue(10);

        Bar d2 = new Bar();
        d2.setColor(Color.parseColor("#FFBB33"));
        d2.setValue(20);

        Bar d3 = new Bar();
        d3.setColor(Color.parseColor("#4F7B13"));
        d3.setValue(15);

        Bar d4 = new Bar();
        d4.setColor(Color.parseColor("#053Bf3"));
        d4.setValue(3);

        Bar d5 = new Bar();
        d5.setColor(Color.parseColor("#10d3a1"));
        d5.setValue(12);

        points.add(d1);
        points.add(d2);
        points.add(d3);
        points.add(d4);
        points.add(d5);

        com.echo.holographlibrary.BarGraph g = rootView.findViewById(R.id.bar_graph_first);
        g.setBars(points);


        Date date = new Date();
        Date beforeDate = new Date();
        beforeDate.setDate(beforeDate.getDate() - 7);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                //.startDate(beforeDate)
                //.endDate(date)
                .defaultSelectedDate(date)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                }
        });



        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(index == 0)
        {
            tv_recom.setText(R.string.reccomendation_1);
            index ++;
        }
        else if(index == 1)
        {
            tv_recom.setText(R.string.reccomendation_2);
            index ++;
        }
        else if(index == 2)
        {
            tv_recom.setText(R.string.reccomendation_3);
            index = 0;
        }

    }
}
