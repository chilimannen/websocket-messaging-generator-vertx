package com.websocket.messaging.generator.Protocol;

/**
 * Created by Robin on 2015-12-16.
 *
 * Transfer object for changing the topic and notifying topic changes.
 */
public class Topic {
    public static final String ACTION = "topic";
    private Header header;
    private String topic;
    private String room;

    public Topic() {
        this.header = new Header(ACTION);
    }

    public Topic(String topic) {
        this(null, topic);
    }

    public Topic(String room, String topic) {
        this.room = room;
        this.topic = topic;
        this.header = new Header(ACTION);
    }


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
