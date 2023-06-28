package com.nikitagorbatko.test_servers.present;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nikitagorbatko.test_servers.R;
import com.nikitagorbatko.test_servers.database.UserDbo;
import com.nikitagorbatko.test_servers.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private View thumbView;
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private String localBalance = "0 BTC";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        thumbView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.custom_seekbar_progress, null, false);
        binding.seekBar.setThumb(getThumb(0));
        binding.seekBar.setOnTouchListener((v, event) -> true);

        binding.firstServer.setNumber(1);
        binding.secondServer.setNumber(2);
        binding.thirdServer.setNumber(3);
        binding.fourthServer.setNumber(4);

        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.Factory.from(MainViewModel.getInitializer(this))
        ).get(MainViewModel.class);

        observe();
    }

    private void observe() {
        binding.buttonStart.setOnClickListener(v -> {
            viewModel.updateBalance();
        });

        binding.buttonBoost.setOnClickListener(v -> {
            binding.firstServer.setServer(this, new BtcServer());
            binding.secondServer.setServer(this, new BtcServer());
            binding.thirdServer.setServer(this, new BtcServer());
            binding.fourthServer.setServer(this, new BtcServer());
        });

        binding.buttonTakeBtc.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/nikitagorbatko/test_servers\n" +
                    "Мой баланс - " +  localBalance);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        final Observer<UserDbo> userObserver = user -> {
            String satoshiBalance = "Satoshi " + user.balance;
            double btcResult = user.balance / 100_000_000.0;
            String btcBalance = String.format("%.8f", btcResult) + " BTC";
            localBalance = btcBalance;
            int progress = (user.balance / 15) * 5;
            if (progress > 100) {
                progress = 100;
            }
            binding.textSatoshi.setText(satoshiBalance);
            binding.textBtc.setText(btcBalance);
            binding.seekBar.setThumb(getThumb(progress));
            binding.seekBar.setProgress(progress);
        };

        viewModel.getUsers().observe(this, userObserver);
    }

    public Drawable getThumb(int progress) {
        ((TextView) thumbView.findViewById(R.id.text_progress)).setText(progress + "%");

        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        thumbView.layout(0, 0, thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight());
        thumbView.draw(canvas);

        return new BitmapDrawable(getResources(), bitmap);
    }
}