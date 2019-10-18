package com.example.arinspect.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.arinspect.database.entity.Row;
import com.example.arinspect.database.entity.RowResponse;

import java.util.List;

/**
 * @author spatel
 * @since 16-10-2019
 */
@Dao
public interface RowDao {

    @Insert
    void insert(Row... rows);

    @Query("DELETE FROM row_table")
    void deleteAllRows();

    @Query("SELECT * FROM row_table WHERE title IS NOT NULL AND title != \"\"")
    LiveData<List<Row>> getAllRows();

    @Query("DELETE FROM row_response_table")
    void deleteAllRowResponse();

    @Insert
    void insert(RowResponse rowResponses);

    @Query("SELECT * FROM row_response_table LIMIT 1")
    RowResponse getRowResponse();

}
