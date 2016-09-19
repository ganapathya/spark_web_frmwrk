package com.myspark.server;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

/**
 * Created by ganapathys on 9/19/2016.
 */
@WebSocket
public class ChatWebSocketHandler {
    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + SparkServer.nextUserNumber++;
        SparkServer.userUsernameMap.put(user, username);
        SparkServer.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = SparkServer.userUsernameMap.get(user);
        SparkServer.userUsernameMap.remove(user);
        SparkServer.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        SparkServer.broadcastMessage(sender = SparkServer.userUsernameMap.get(user), msg = message);
    }


}
