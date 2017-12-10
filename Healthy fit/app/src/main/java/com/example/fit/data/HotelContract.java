package com.example.fit.data;

/**
 * Created by Евгений on 09.12.2017.
 */

import android.provider.BaseColumns;

public final class HotelContract {

    private HotelContract() {
    };

    public static final class GuestEntry implements BaseColumns {
        public final static String TABLE_NAME = "fit_track_data";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DATA = "data";
        public final static String COLUMN_TIME = "time";
        public final static String COLUMN_ALL_SLEEP_TIME = "all_sleep_time";
        public final static String COLUMN_SLEEP_TIME = "sleep_time";
        public final static String COLUMN_SLEEP_CNT = "sleep_cnt";
        public final static String COLUMN_LIGHT_TIME = "light_time";
        public final static String COLUMN_LITE_CNT = "lite_cnt";
        public final static String COLUMN_DEEP_TIME = "deep_time";
        public final static String COLUMN_DEEP_CNT = "deep_cnt";
        public final static String COLUMN_REM_TIME = "rem_time";
        public final static String COLUMN_REM_CNT = "rem_cnt";
    }
}
