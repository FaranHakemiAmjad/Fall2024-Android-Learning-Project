package org.bihe.faranha.lowercaseimdb.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.bihe.faranha.lowercaseimdb.data.objects.Report;
import org.bihe.faranha.lowercaseimdb.data.objects.ReportCategory;

import java.util.List;

@Dao
public interface ReportDao {

    @Insert
    long insert(Report report);

    @Update
    int update(Report report);

    @Query("DELETE FROM Report WHERE id = :id")
    int delete(long id);

    @Query("SELECT * FROM Report")
    List<Report> getAll();

    @Query("SELECT * FROM Report WHERE sender = :sender")
    List<Report> getAllUserReports(String sender);

    @Query("SELECT * FROM Report WHERE category = :category AND sender = :sender")
    List<Report> getAllUserReportCategory(ReportCategory category, String sender);

    @Query("SELECT * FROM Report WHERE category = :category")
    List<Report> getAllReportCategory(ReportCategory category);
}
