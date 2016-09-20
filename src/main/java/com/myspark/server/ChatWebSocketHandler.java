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
        String username = "User" + ChatServer.nextUserNumber++;
        ChatServer.userUsernameMap.put(user, username);
        ChatServer.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = ChatServer.userUsernameMap.get(user);
        ChatServer.userUsernameMap.remove(user);
        ChatServer.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        ChatServer.broadcastMessage(sender = ChatServer.userUsernameMap.get(user), msg = message);
    }


}
