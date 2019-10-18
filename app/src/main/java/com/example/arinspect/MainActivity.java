package com.example.arinspect;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.arinspect.database.entity.Row;
import com.example.arinspect.databinding.ActivityMainBinding;
import com.example.arinspect.utils.EqualSpacingItemDecoration;

import java.util.List;

/**
 * @author spatel
 * @since 16-10-2019
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mActivityMainBinding;
    private RowViewModel mRowViewModel;
    private RowRecyclerviewAdapter mRowRecyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mActivityMainBinding.rowRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mActivityMainBinding.rowRecyclerview.addItemDecoration(new EqualSpacingItemDecoration(getResources()
                .getDimensionPixelOffset(R.dimen.size_10)));
        mRowRecyclerviewAdapter = new RowRecyclerviewAdapter();
        mActivityMainBinding.rowRecyclerview.setAdapter(mRowRecyclerviewAdapter);
        mRowViewModel = ViewModelProviders.of(this).get(RowViewModel.class);
        mRowViewModel.getAllRows().observe(this, new Observer<List<Row>>() {
            @Override
            public void onChanged(List<Row> rowList) {
                mRowRecyclerviewAdapter.setRowList(rowList);
            }
        });
        mRowViewModel.getTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String title) {
                Log.i(TAG, "title: " + title);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(title);
                }
            }
        });

        mActivityMainBinding.swipeToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRowViewModel.getAllRows();
                mActivityMainBinding.swipeToRefreshLayout.setRefreshing(false);
            }
        });
    }
}