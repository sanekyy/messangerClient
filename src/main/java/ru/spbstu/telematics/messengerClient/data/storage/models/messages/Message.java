package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */
@Getter
@Setter
public abstract class Message implements Serializable {

    Long id;
    Long senderId;
    Type type;

    String token = "";

    public enum Type {
        // Сообщения от клиента к серверу
        MSG_REGISTRATION, // в ответ MSG_STATUS
        MSG_LOGIN, // в ответ MSG_STATUS
        MSG_TEXT, // в ответ MSG_STATUS
        MSG_INFO, // в ответ MSG_INFO_RESULT
        MSG_CHAT_LIST, // в ответ MSG_CHAT_LIST_RESULT,
        MSG_CHAT_CREATE, // в ответ MSG_STATUS
        MSG_CHAT_HIST, // в ответ MSG_CHAT_HIST_RESULT,

        // Сообщения от сервера клиенту
        MSG_STATUS,
        MSG_CHAT_LIST_RESULT,
        MSG_CHAT_HIST_RESULT,
        MSG_INFO_RESULT
    }
}
