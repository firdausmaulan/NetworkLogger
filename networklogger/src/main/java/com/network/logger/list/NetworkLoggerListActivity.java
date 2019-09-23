package com.network.logger.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.network.logger.R;
import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;
import com.network.logger.detail.NetworkLoggerDetailActivity;
import com.network.logger.util.Constant;

import java.util.List;

public class NetworkLoggerListActivity extends AppCompatActivity implements NetworkLoggerView {

    private NetworkLoggerPresenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private ImageView ivBack;
    private ImageView ivDelete;
    private RecyclerView rvVolleyLogger;
    private NetworkLoggerAdapter networkLoggerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_logger_list);

        setView();
        setAction();

        presenter = new NetworkLoggerPresenter(this, AppDatabase.getAppDatabase());
        presenter.getListData(0);
    }

    private void setView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        ivBack = findViewById(R.id.ivBack);
        ivDelete = findViewById(R.id.ivDelete);
        rvVolleyLogger = findViewById(R.id.rvVolleyLogger);

        networkLoggerAdapter = new NetworkLoggerAdapter(this);
        rvVolleyLogger.setAdapter(networkLoggerAdapter);
    }

    private void setAction() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListData(0);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkLoggerAdapter.clear();
                presenter.deleteAllData();
            }
        });

        networkLoggerAdapter.setClickListener(new NetworkLoggerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, NetworkLoggerModel model) {
                Intent intent = new Intent(NetworkLoggerListActivity.this, NetworkLoggerDetailActivity.class);
                intent.putExtra(Constant.UID, model.getUid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showData(List<NetworkLoggerModel> list) {
        networkLoggerAdapter.addList(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
    }
}
