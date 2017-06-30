package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatHistResultMessage extends Message {

    public static final int STATUS_SUCCESS = -1;

    public static final int PERMISSION_DENIED_ERROR = 1;
    public static final int CHAT_NOT_EXIST = 2;


    private int statusCode;

    List<TextMessage> messages;

}
