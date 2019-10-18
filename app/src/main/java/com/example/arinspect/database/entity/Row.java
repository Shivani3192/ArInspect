package com.example.arinspect.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * @author spatel
 * @since 16-10-2019
 */
@Entity(tableName = "row_table")
public class Row {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String description;

    @SerializedName("imageHref")
    public String imageUrl;
}
