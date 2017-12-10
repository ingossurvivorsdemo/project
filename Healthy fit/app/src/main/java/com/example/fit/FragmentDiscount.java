package com.example.fit;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentDiscount extends Fragment implements View.OnClickListener{

    TextView tv_start_date;
    TextView tv_stop_date;

    int quantity_sleep;
    int quality_sleep;
    int discount;
    int cashback;

    TextView tv_quantity_sleep;
    TextView tv_quality_sleep;
    TextView tv_discount;
    TextView tv_cashback;




    int DIALOG_DATE = 1;
    int myYear = 2011;
    int myMonth = 02;
    int myDay = 03;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discount, container, false);

        tv_start_date = (TextView) rootView.findViewById(R.id.date_start);
        tv_stop_date = (TextView) rootView.findViewById(R.id.date_stop);

        tv_quantity_sleep = (TextView) rootView.findViewById(R.id.textView_quantity_sleep);
        tv_quality_sleep = (TextView) rootView.findViewById(R.id.textView_quality_sleep);
        tv_discount = (TextView) rootView.findViewById(R.id.textView_discount);
        tv_cashback = (TextView) rootView.findViewById(R.id.textView_cashback);

        tv_start_date.setOnClickListener(this);
        tv_stop_date.setOnClickListener(this);

        tv_quantity_sleep.setText(String.valueOf(quantity_sleep));
        tv_quality_sleep.setText(String.valueOf(quality_sleep));
        tv_discount.setText(String.valueOf(discount));
        tv_cashback.setText(String.valueOf(cashback));

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.date_start:
                Toast toast = Toast.makeText(getContext(), "выбор даты начала", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.date_stop:
                toast = Toast.makeText(getContext(), "выбор даты конца", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}
