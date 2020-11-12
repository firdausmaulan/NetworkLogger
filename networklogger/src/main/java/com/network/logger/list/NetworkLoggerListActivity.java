package com.network.logger.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private ImageView ivSearch;
    private NetworkLoggerAdapter networkLoggerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_logger_list);

        setView();
        setAction();

        presenter = new NetworkLoggerPresenter(this, this, AppDatabase.getAppDatabase());
        presenter.getListData();
    }

    private void setView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        ivBack = findViewById(R.id.ivBack);
        ivDelete = findViewById(R.id.ivDelete);
        ivSearch = findViewById(R.id.ivSearch);
        RecyclerView rvVolleyLogger = findViewById(R.id.rvVolleyLogger);

        networkLoggerAdapter = new NetworkLoggerAdapter(this);
        rvVolleyLogger.setAdapter(networkLoggerAdapter);
    }

    private void setAction() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkLoggerAdapter.clear();
                presenter.getListData();
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
                showDeleteDialog();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NetworkLoggerListActivity.this, NetworkLoggerListSearchActivity.class);
                startActivity(intent);
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

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        networkLoggerAdapter.clear();
                        presenter.deleteAllData();
                    }
                }).create().show();
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
