package com.nikitagorbatko.test_servers.present;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.nikitagorbatko.test_servers.R;
import com.nikitagorbatko.test_servers.databinding.ServerPanelBinding;


public class CustomServerView extends ConstraintLayout {
    private final ServerPanelBinding binding;
    private State state = State.STOP;
    private final Drawable drawablePanel;
    private final Drawable drawablePointer;
    private float lastAnimatorValue = -90f;
    private final ObjectAnimator animator;
    private BtcServer server;

    public CustomServerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        drawablePanel = context.getDrawable(R.drawable.ic_indicator_panel);
        drawablePointer = context.getDrawable(R.drawable.ic_pointer);
        binding = ServerPanelBinding.inflate(LayoutInflater.from(context));
        addView(binding.getRoot());
        animator = ObjectAnimator.ofFloat(binding.framePointer, View.ROTATION, -90f, 0f);
    }

    public void setNumber(int number) {
        String numberString = "SERVER " + number;
        binding.textServer.setText(numberString);
    }

    public void setServer(LifecycleOwner lifecycleOwner, BtcServer server) {
        if (this.server != null) return;
        this.server = server;

        final Observer<Integer> pingObserver = ping -> {
            String pingString = "" + ping;
            binding.textPingValue.setText(pingString);
            if (state == State.STOP) {
                state = State.RUNNING;
                binding.imagePanel.setImageDrawable(drawablePanel);
                binding.imagePointer.setImageDrawable(drawablePointer);
            }
            buildAnimator(ping);
        };
        server.ping.observe(lifecycleOwner, pingObserver);
    }

    private void buildAnimator(int ping) {
        float newValue = (90f / 50f) * ping - 90f;
        animator.setFloatValues(lastAnimatorValue, newValue);
        animator.setDuration(700);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        lastAnimatorValue = newValue;
    }

    private enum State {
        STOP, RUNNING
    }
}
