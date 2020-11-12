package com.network.logger.util;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.fragment.app.FragmentActivity;

import java.util.Timer;
import java.util.TimerTask;

public class EditTextDelay implements TextWatcher {

    private final FragmentActivity context;
    private Timer timer = new Timer();

    public Listener listener;

    public interface Listener {
        void onTextChanged(String data);
    }

    public TextWatcher setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public EditTextDelay(FragmentActivity context) {
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(final Editable editable) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (context == null) return;
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onTextChanged(editable.toString().trim());
                            }
                        });
                    }
                }, 2000);
    }
}
