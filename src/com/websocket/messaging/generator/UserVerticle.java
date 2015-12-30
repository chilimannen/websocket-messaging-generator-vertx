package com.websocket.messaging.generator;

import com.websocket.messaging.generator.Protocol.*;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Robin on 2015-12-29.
 * <p>
 * User simulation.
 */
public class UserVerticle implements Verticle {
    private Vertx vertx;

    @Override
    public Vertx getVertx() {
        return vertx;
    }

    @Override
    public void init(Vertx vertx, Context context) {
        this.vertx = vertx;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        lookup("Public room");
    }

    private void lookup(String room) {
        vertx.createHttpClient().websocket(
                Configuration.REGISTRY_PORT,
                Configuration.REGISTRY_HOST,
                "/", event -> {

                    event.handler(data -> {
                        Index index = (Index) Serializer.unpack(data.toString(), Index.class);

                        if (!index.getFull())
                            connect(index);
                    });

                    sendBus(event.textHandlerID(), new Lookup(room));
                });
    }

    private void connect(Index index) {
        vertx.createHttpClient().websocket(index.getPort(), index.getIp(), "/", event -> {

            event.handler(data -> {
                Packet packet = (Packet) Serializer.unpack(data.toString(), Packet.class);

                switch (packet.getAction()) {
                    case Authenticate.ACTION:
                        sendBus(event.textHandlerID(), new Join("Public room"));
                        break;
                    case Token.ACTION:
                        sendBus(event.textHandlerID(), new Join("Public room"));
                    default:
                        break;
                }

            });

            if (Configuration.USE_TOKEN_AUTH)
                authenticateAccountByToken(event.textHandlerID());
            else
                sendBus(event.textHandlerID(), new Authenticate(UUID.randomUUID().toString(), "pass_kitten"));

            vertx.setPeriodic(Configuration.MESSAGE_RATE, timer -> {
                sendBus(event.textHandlerID(), new Message("message-message-message"));
            });
        });
    }

    private void authenticateAccountByToken(String address) {
        String username = UUID.randomUUID().toString();
        Long expiry = new Date().getTime() * 1000;
        try {
            String key = Authentication.SignToken(username, expiry);
            sendBus(address, new Token(username, key, expiry));
        } catch (TokenException e) {
            e.printStackTrace();
        }
    }

    private void sendBus(String address, Object data) {
        vertx.eventBus().send(address, Serializer.pack(data));
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        vertx.close();
    }
}
