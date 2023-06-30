package com.example.chat.socket.io.event;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatEventHandler {

    private final SocketIOServer server;

    public ChatEventHandler(SocketIOServer server) {
        this.server = server;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("client_send_room", String.class, onEventJoinRoom());
        server.addEventListener("client_send_message", Message.class, onEventSendMessage());
    }

    private ConnectListener onConnected() {
        return new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                log.info("Client connected: {}", socketIOClient.getSessionId());
            }
        };
    }


    private DisconnectListener onDisconnected () {
        return new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                log.info("Client disconnected: {}", socketIOClient.getSessionId());
            }
        };
    }

    private DataListener<String> onEventJoinRoom() {
        return new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String roomNumber, AckRequest ackRequest) throws Exception {
                log.info("User {} join room: {}", client.getSessionId(), roomNumber);
                client.joinRoom(roomNumber);
            }
        };
    }

    private DataListener<Message> onEventSendMessage() {
        return new DataListener<Message>() {
            @Override
            public void onData(SocketIOClient client, Message message, AckRequest ackRequest) throws Exception {
                log.info("User {} send message: {}", client.getSessionId(), message.getContent());
                server.getRoomOperations(message.getRoomNumber()).sendEvent("server_send_message", message);
            }
        };
    }


}
