package org.bihe.faranha.lowercaseimdb.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.bihe.faranha.lowercaseimdb.data.dao.ReportDao;
import org.bihe.faranha.lowercaseimdb.data.dao.UserDao;
import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.data.objects.User;

@Database(entities = {User.class, Report.class}, version = 2)
@TypeConverters({org.bihe.faranha.lowercaseimdb.data.db.TypeConverters.class})
public abstract class DbManager extends RoomDatabase {

    private static final String DB_NAME = "LowercaseIMDB";
    private static DbManager dbManager;
    public abstract UserDao userDao();

    public abstract ReportDao reportDao();

    public static DbManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = Room.databaseBuilder(context, DbManager.class,DB_NAME).fallbackToDestructiveMigration().build();
        }
        return dbManager;
    }
}
