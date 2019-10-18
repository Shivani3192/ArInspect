package com.example.arinspect.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.arinspect.database.dao.RowDao;
import com.example.arinspect.database.entity.Row;
import com.example.arinspect.database.entity.RowResponse;

/**
 * @author spatel
 * @since 16-10-2019
 */
@Database(entities = {Row.class, RowResponse.class}, version = 1)
public abstract class ArInspectDatabase extends RoomDatabase {
    private static final String DB_NAME = "rowDatabase.db";
    private static volatile ArInspectDatabase instance;

    public static synchronized ArInspectDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static ArInspectDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                ArInspectDatabase.class,
                DB_NAME).fallbackToDestructiveMigration().build();
    }

    public abstract RowDao getRowDao();
}
