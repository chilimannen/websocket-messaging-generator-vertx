package com.websocket.messaging.generator;

import io.vertx.core.*;

/**
 * Created by Robin on 2015-12-29.
 *
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
        vertx.deployVerticle("com.websocket.messaging.generator.UserVerticle",
                new DeploymentOptions()
                    .setInstances(Configuration.USER_INSTANCES));
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        vertx.close();
    }
}
