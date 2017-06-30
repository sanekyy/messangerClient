package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import lombok.Getter;
import ru.spbstu.telematics.messengerClient.store.User;



@Getter
public class StatusMessage extends Message {

    public static final int LOGIN_SUCCESS = -1;
    public static final int REGISTRATION_SUCCESS = -2;
    public static final int CHAT_CREATE_SUCCESS = -3;
    public static final int TEXT_MESSAGE_SUCCESS = -4;

    public static final int LOGIN_ERROR = 1;
    public static final int REGISTRATION_ERROR = 2;
    public static final int CHAT_CREATE_ERROR = 3;
    public static final int USER_NOT_EXIST_ERROR = 4;
    public static final int SERVER_ERROR = 5;
    public static final int MESSAGE_TOO_LONG = 6;
    public static final int CHAT_NOT_EXIST = 7;
    public static final int PERMISSION_DENIED = 8;


    private int statusCode;

    User user;
}
