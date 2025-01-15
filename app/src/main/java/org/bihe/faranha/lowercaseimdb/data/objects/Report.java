package org.bihe.faranha.lowercaseimdb.data.objects;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

@Entity
public class Report implements Serializable {
    private String title;
    private String body;
    @ColumnInfo(name = "category")
    private ReportCategory category;
    private Date date;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String sender;
    @ColumnInfo(name = "image url")
    private Uri imageUrl;

    public Report(String sender, String title, String body, ReportCategory category, Date date, Uri imageUrl) {
        this.sender = sender;
        this.title = title;
        this.body = body;
        this.category = category;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ReportCategory getCategory() {
        return category;
    }

    public void setCategory(ReportCategory category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Uri getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
    }
}
