package com.websocket.messaging.generator;

import io.vertx.core.*;

import java.util.ArrayList;

/**
 * Created by Robin on 2015-12-29.
 * <p>
 * Starts up the generator.
 */
public class Launcher extends AbstractVerticle {
    private Vertx vertx;

    @Override
    public void init(Vertx vertx, Context context) {
        this.vertx = vertx;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        final ArrayList<Integer> counter = new ArrayList<>();

        if (Configuration.USER_SPAWN_DELAY == 0) {
            for (int i = 0; i < Configuration.USER_INSTANCES; i++)
                vertx.deployVerticle(new UserVerticle());
        } else
            vertx.setPeriodic(Configuration.USER_SPAWN_DELAY, user -> {
                if (counter.size() < Configuration.USER_INSTANCES) {
                    vertx.deployVerticle(new UserVerticle());
                    counter.add(0);
                }
            });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        vertx.close();
    }
}
