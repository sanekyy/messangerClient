package ru.spbstu.telematics.messengerClient.network;


import lombok.Getter;
import lombok.Setter;
import ru.spbstu.telematics.messengerClient.AppConfig;
import ru.spbstu.telematics.messengerClient.data.DataManager;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.ProtocolException;
import ru.spbstu.telematics.messengerClient.logic.CommandHandler;
import ru.spbstu.telematics.messengerClient.store.User;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */

@Setter
@Getter
public class Session {

    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private User user;

    // сокет на клиента
    private Socket socket;

    private IProtocol protocol = DataManager.getInstance().getProtocol();

    public Session(Socket socket) {
        this.socket = socket;
    }

    public boolean isLoggedIn() {
        return user != null && !"".equals(user.getToken());
    }

    public void send(Message message) {
        if (isLoggedIn()) {
            message.setSenderId(user.getId());
            message.setToken(user.getToken());
        }

        ByteBuffer buffer;
        try {
            buffer = ByteBuffer.wrap(protocol.encode(message));
        } catch (ProtocolException e) {
            if (AppConfig.DEBUG) {
                e.printStackTrace();
            }
            return;
        }

        try {
            socket.getChannel().write(buffer);
        } catch (IOException e) {
            if (AppConfig.DEBUG) {
                e.printStackTrace();
            }
            System.out.println(e.getMessage());
        }
    }

    public void onMessage(Message message) {
        switch (message.getType()) {
            case MSG_TEXT:
                CommandHandler.text(this, message);
                break;
            case MSG_STATUS:
                CommandHandler.status(this, message);
                break;
            case MSG_INFO_RESULT:
                CommandHandler.infoResult(this, message);
                break;
            case MSG_CHAT_LIST_RESULT:
                CommandHandler.chatListResult(this, message);
                break;
            case MSG_CHAT_HIST_RESULT:
                CommandHandler.chatHistResult(this, message);
                break;
        }
    }

    public void logout() {
        user = null;
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                if (AppConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }
}