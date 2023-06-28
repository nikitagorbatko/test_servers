package com.nikitagorbatko.test_servers.present;

import androidx.lifecycle.MutableLiveData;

import java.util.Random;

public class BtcServer {
    private Random random = new Random();
    private Thread thread;
    MutableLiveData<Integer> ping = new MutableLiveData<Integer>(0);

    public BtcServer() {
        runServer();
    }

    private void runServer() {
        if (thread == null) {
            thread = new Thread(() -> {
                try {
                    Thread.sleep(Integer.toUnsignedLong(random.nextInt(3000)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                while(true) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ping.postValue(random.nextInt(99));
                }
            });
            thread.start();
        }
    }
}
