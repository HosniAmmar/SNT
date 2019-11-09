package com.img.snt.Model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Item implements Comparable<Item> {
    @PrimaryKey
    @NonNull
    public String id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "content")
    public String content;
    @ColumnInfo(name = "pubDate")
    public String pubDate;

    @ColumnInfo(name = "sourceName")
    public String sourceName;
    @ColumnInfo(name = "language")
    public String language;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "priority")
    public int priority;
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;

    public Item(@NonNull String id, String title, String content, String pubDate, String sourceName, String language, String category, String type, int priority, String thumbnail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pubDate = pubDate;
        this.sourceName = sourceName;
        this.language = language;
        this.category = category;
        this.type = type;
        this.priority = priority;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    @SuppressLint("SimpleDateFormat")
    public Date GetPubDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.pubDate);
    }
    @Override
    public int compareTo(@NonNull Item o) {
        try {
            if (GetPubDate() == null || o.GetPubDate() == null) {
                return 0;
            }
            return GetPubDate().compareTo(o.GetPubDate());
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
