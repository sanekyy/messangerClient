package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import lombok.Getter;
import ru.spbstu.telematics.messengerClient.store.User;

/**
 * Created by ihb on 17.06.17.
 */

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
    public static final int TEXT_MESSAGE_ERROR = 5;


    private int statusCode;

    User user;
}
