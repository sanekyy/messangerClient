package ru.spbstu.telematics.messangerClient.network;


import ru.spbstu.telematics.messangerClient.exceptions.ProtocolException;
import ru.spbstu.telematics.messangerClient.logic.CommandHandler;
import ru.spbstu.telematics.messangerClient.messages.Message;
import ru.spbstu.telematics.messangerClient.store.User;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
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
        return user != null;
    }

    public void send(Message message){
        // TODO: 17.06.17 fix me
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
            case MSG_STATUS:
                CommandHandler.status(this, message);
        }
    }

    public void close() {
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }
}