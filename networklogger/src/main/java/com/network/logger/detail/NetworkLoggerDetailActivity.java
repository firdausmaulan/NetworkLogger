package com.network.logger.detail;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.network.logger.R;
import com.network.logger.database.AppDatabase;
import com.network.logger.util.Constant;

public class NetworkLoggerDetailActivity extends AppCompatActivity implements NetworkLoggerDetailView {

    private NetworkLoggerDetailPresenter presenter;

    private TextView tvData;
    private ImageView ivBack;
    private ImageView ivShare;
    private ProgressBar progressBarShare;
    private RelativeLayout progressBar;

    private String mData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_logger_detail);

        int uid = getIntent().getIntExtra(Constant.UID, 0);

        setView();
        setAction();

        presenter = new NetworkLoggerDetailPresenter(this, this, AppDatabase.getAppDatabase());
        presenter.getData(uid);
    }

    private void setView() {
        tvData = findViewById(R.id.tvData);
        ivBack = findViewById(R.id.ivBack);
        ivShare = findViewById(R.id.ivShare);
        progressBarShare = findViewById(R.id.progressBarShare);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setAction() {
        ivBack.setOnClickListener(view -> finish());

        ivShare.setOnClickListener(view -> {
            ivShare.setVisibility(View.GONE);
            progressBarShare.setVisibility(View.VISIBLE);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mData);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });
    }

    @Override
    public void showData(String data) {
        mData = data;
        progressBar.setVisibility(View.GONE);
        tvData.setText(mData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ivShare != null && progressBarShare != null){
            ivShare.setVisibility(View.VISIBLE);
            progressBarShare.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
    }
}
