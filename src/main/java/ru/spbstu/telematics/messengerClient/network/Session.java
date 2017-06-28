package ru.spbstu.telematics.messengerClient.network;


import lombok.Getter;
import lombok.Setter;
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

    public Session(Socket socket) {
        this.socket = socket;
    }

    public boolean isLoggedIn(){
        return user != null && !"".equals(user.getToken());
    }

    public void send(Message message){
        // TODO: 17.06.17 fix me

        if (isLoggedIn()) {
            message.setSenderId(user.getId());
            message.setToken(user.getToken());
        }

        ByteBuffer buffer = null;
        try {
            buffer = ByteBuffer.wrap(new StringProtocol().encode(message));
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        try {
            socket.getChannel().write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(Message message) {
        switch (message.getType()){
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
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }
}