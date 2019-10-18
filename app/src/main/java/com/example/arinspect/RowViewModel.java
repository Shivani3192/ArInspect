package com.example.arinspect;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arinspect.database.entity.Row;
import com.example.arinspect.database.entity.RowResponse;

import java.util.List;

/**
 * @author spatel
 * @since 16-10-2019
 */
public class RowViewModel extends AndroidViewModel {

    private RowRepository mRowRepository;

    public RowViewModel(@NonNull Application application) {
        super(application);
        mRowRepository = new RowRepository(getApplication());
    }

    LiveData<List<Row>> getAllRows() {
        return mRowRepository.getAllRows();
    }

    LiveData<String> getTitle(){
        return mRowRepository.getTitle();
    }
}
