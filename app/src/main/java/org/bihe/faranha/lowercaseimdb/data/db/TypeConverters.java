package org.bihe.faranha.lowercaseimdb.data.db;

import android.net.Uri;

import androidx.room.TypeConverter;

import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class TypeConverters {

    @TypeConverter
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static Date longToDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static String categoryToString(ReportCategory value) {
        return String.valueOf(value);
    }

    @TypeConverter
    public static ReportCategory stringToCategory(String value) {
        return ReportCategory.valueOf(value);
    }

    @TypeConverter
    public static String urlToString(Uri value) {
        return String.valueOf(value);
    }

    @TypeConverter
    public static Uri stringToUrl(String value) {
        return Uri.parse(value);
    }
}
