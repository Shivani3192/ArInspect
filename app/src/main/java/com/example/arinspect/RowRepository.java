package com.example.arinspect;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.arinspect.database.ArInspectDatabase;
import com.example.arinspect.database.dao.RowDao;
import com.example.arinspect.database.entity.Row;
import com.example.arinspect.database.entity.RowResponse;
import com.example.arinspect.network.ApiClient;
import com.example.arinspect.network.ApiInterface;
import com.example.arinspect.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author spatel
 * @since 16-10-2019
 */
class RowRepository {

    private ApiClient mApiClient;
    private RowDao mRowDao;
    private LiveData<List<Row>> mRowList;
    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    private Application mApp;

    RowRepository(Application application) {
        mApp = application;
        mRowDao = ArInspectDatabase.getInstance(application).getRowDao();
        mRowList = mRowDao.getAllRows();
        new GetTitleAsyncTask().execute();
        mTitle.setValue(application.getResources().getString(R.string.app_name));
        mApiClient = ApiClient.getInstance();
        mApiClient.create();
        getRowsFromCloud();
    }

    private void getRowsFromCloud() {
        if (NetworkUtils.isNetworkAvailable(mApp)) {
            Call<RowResponse> call = mApiClient.createService(ApiInterface.class).getRowList();
            call.enqueue(new Callback<RowResponse>() {
                @Override
                public void onResponse(Call<RowResponse> call, Response<RowResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        delete();
                        insert(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RowResponse> call, Throwable t) {

                }
            });
        }
    }

    LiveData<List<Row>> getAllRows() {
        return mRowList;
    }

    private void insert(RowResponse rowResponse) {
        new InsertRowsAsyncTask(rowResponse).execute();
    }

    private void delete() {
        new DeleteRowsAsyncTask().execute();
    }

    LiveData<String> getTitle() {
        return mTitle;
    }

    private class InsertRowsAsyncTask extends AsyncTask<Void, Void, Void> {

        private RowResponse rowResponse;

        InsertRowsAsyncTask(RowResponse rowResponse) {
            this.rowResponse = rowResponse;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRowDao.insert(rowResponse);
            mRowDao.insert(rowResponse.rows);
            mTitle.postValue(rowResponse.title);
            return null;
        }
    }

    private class DeleteRowsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mRowDao.deleteAllRows();
            mRowDao.deleteAllRowResponse();
            return null;
        }
    }

    private class GetTitleAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            RowResponse rowResponse = mRowDao.getRowResponse();
            if (rowResponse != null) {
                mTitle.postValue(rowResponse.title);
            }
            return null;
        }
    }
}