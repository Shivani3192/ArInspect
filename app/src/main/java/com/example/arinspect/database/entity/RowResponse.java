package com.example.arinspect.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author spatel
 * @since 16-10-2019
 */
@Entity(tableName = "row_response_table")
public class RowResponse {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    @Ignore
    public Row[] rows;
}
