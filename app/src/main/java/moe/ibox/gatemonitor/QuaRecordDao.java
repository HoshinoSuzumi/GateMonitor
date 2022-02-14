package moe.ibox.gatemonitor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class QuaRecordDao {
    private final SQLiteDatabase db;

    private String[] columns = null;
    private String selection = null;
    private String[] selectionArgs = null;

    public QuaRecordDao(SQLiteDatabase db) {
        this.db = db;
    }

    public QuaRecordDao(Context context) {
        this.db = new SqliteDbHelper(context).getWritableDatabase();
    }

    public long insertRecord(double temperature, double humidity, double co2, double noise) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("temperature", temperature);
        contentValues.put("humidity", humidity);
        contentValues.put("co2", co2);
        contentValues.put("noise", noise);
        contentValues.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        return this.db.insert("qua_channel_records", null, contentValues);
    }

    public long insertRecord(QuaChannelData quaChannelData) {
        return this.insertRecord(
                quaChannelData.getTemperature(),
                quaChannelData.getHumidity(),
                quaChannelData.getCo2(),
                quaChannelData.getNoise()
        );
    }

    @SuppressLint("Range")
    public ArrayList<QuaChannelData> queryQuaChannelData(String[] columns, String selection, String[] selectionArgs, String groupBy, String orderBy) {
        if (columns == null) columns = this.columns;
        if (selection == null) selection = this.selection;
        if (selectionArgs == null) selectionArgs = this.selectionArgs;
        Cursor cursor = this.db.query(
                "qua_channel_records",
                columns,
                selection,
                selectionArgs,
                groupBy,
                null,
                orderBy
        );
        ArrayList<QuaChannelData> quaChannelDataList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isLast()) {
            quaChannelDataList.add(new QuaChannelData(
                            cursor.getDouble(cursor.getColumnIndexOrThrow("temperature")),
                            cursor.getDouble(cursor.getColumnIndexOrThrow("humidity")),
                            cursor.getDouble(cursor.getColumnIndexOrThrow("co2")),
                            cursor.getDouble(cursor.getColumnIndexOrThrow("noise")),
                            cursor.getString(cursor.getColumnIndexOrThrow("time"))
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
        return quaChannelDataList;
    }

    public ArrayList<QuaChannelData> queryQuaChannelData() {
        return this.queryQuaChannelData(null, null, null, null, "time DESC");
    }

    public ArrayList<QuaChannelData> queryQuaChannelData(String from, String to) {
        this.selection = "time between ? and ?";
        this.selectionArgs = new String[]{from, to};
        return this.queryQuaChannelData(null, this.selection, this.selectionArgs, null, "time DESC");
    }

    public ArrayList<QuaChannelData> queryQuaChannelData(String[] columns) {
        this.columns = columns;
        return this.queryQuaChannelData(this.columns, null, null, null, "time DESC");
    }

    public ArrayList<QuaChannelData> queryQuaChannelData(String[] columns, String from, String to) {
        this.columns = columns;
        this.selection = "time between ? and ?";
        this.selectionArgs = new String[]{from, to};
        return this.queryQuaChannelData(this.columns, this.selection, this.selectionArgs, null, "time DESC");
    }
}
