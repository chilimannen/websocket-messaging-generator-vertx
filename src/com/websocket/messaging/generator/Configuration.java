package com.websocket.messaging.generator;

/**
 * Created by Robin on 2015-12-29.
 *
 * User and entry point configuration.
 */
public class Configuration {
    public static final Integer REGISTRY_PORT = 6090;
    public static final String REGISTRY_HOST = "localhost";
    public static final long MESSAGE_RATE = 9150;
    public static final boolean USE_TOKEN_AUTH = true;
    public static Integer USER_INSTANCES = 120;
    public static Integer USER_SPAWN_DELAY = 95;
}
