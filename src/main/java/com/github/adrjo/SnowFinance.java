package com.github.adrjo;

public class SnowFinance {
    public final SnowFinance instance;
    public SnowFinance() {
        instance = this;

        System.out.println("Starting SnowFinance...");
        long now = System.currentTimeMillis();
        init();
        System.out.printf("Started in %dms", System.currentTimeMillis() - now);

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    private void init() {
        //TODO: load data
    }

    private void shutdown() {
        //TODO: close and save data

    }
}
