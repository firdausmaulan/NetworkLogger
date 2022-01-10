package com.network.logger.list;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.network.logger.R;
import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;
import com.network.logger.detail.NetworkLoggerDetailActivity;
import com.network.logger.util.Constant;
import com.network.logger.util.EditTextDelay;

import java.util.List;

public class NetworkLoggerListSearchActivity extends AppCompatActivity implements NetworkLoggerView {

    private NetworkLoggerPresenter presenter;

    private EditText etSearch;
    private EditTextDelay editTextDelay;
    private ImageView ivBack;
    private NetworkLoggerAdapter networkLoggerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_logger_search);

        setView();
        setAction();

        presenter = new NetworkLoggerPresenter(this, this, AppDatabase.getAppDatabase());
    }

    private void setView() {
        ivBack = findViewById(R.id.ivBack);
        etSearch = findViewById(R.id.etSearch);
        editTextDelay = new EditTextDelay(this);
        RecyclerView rvVolleyLogger = findViewById(R.id.rvVolleyLogger);
        networkLoggerAdapter = new NetworkLoggerAdapter(this);
        rvVolleyLogger.setAdapter(networkLoggerAdapter);
    }

    private void setAction() {
        ivBack.setOnClickListener(view -> finish());

        etSearch.addTextChangedListener(editTextDelay.setListener(data -> {
            networkLoggerAdapter.clear();
            if (data != null && !data.isEmpty()) presenter.getSearchData(data);
        }));

        networkLoggerAdapter.setClickListener((view, model) -> {
            Intent intent = new Intent(this, NetworkLoggerDetailActivity.class);
            intent.putExtra(Constant.UID, model.getUid());
            startActivity(intent);
        });
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
