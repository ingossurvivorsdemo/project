package com.example.fit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Евгений on 09.12.2017.
 */

public class HotelDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HotelDbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "fit_track_data.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link HotelDbHelper}.
     *
     * @param context Контекст приложения
     */
    public HotelDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + HotelContract.GuestEntry.TABLE_NAME + " ("
                + HotelContract.GuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HotelContract.GuestEntry.COLUMN_DATA + " DATETIME, "
                + HotelContract.GuestEntry.COLUMN_TIME + " DATETIME, "
                + HotelContract.GuestEntry.COLUMN_ALL_SLEEP_TIME + " DATETIME, "
                + HotelContract.GuestEntry.COLUMN_SLEEP_TIME + " DATETIME);"
                + HotelContract.GuestEntry.COLUMN_SLEEP_CNT + " INTEGER NOT NULL DEFAULT 0);"
                + HotelContract.GuestEntry.COLUMN_LIGHT_TIME + " DATETIME);"
                + HotelContract.GuestEntry.COLUMN_LITE_CNT + " INTEGER NOT NULL DEFAULT 0);"
                + HotelContract.GuestEntry.COLUMN_DEEP_TIME + " DATETIME);"
                + HotelContract.GuestEntry.COLUMN_DEEP_CNT + " INTEGER NOT NULL DEFAULT 0);"
                + HotelContract.GuestEntry.COLUMN_REM_TIME + " DATETIME);"
                + HotelContract.GuestEntry.COLUMN_REM_CNT + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXISTS " + HotelContract.GuestEntry.TABLE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }
}
