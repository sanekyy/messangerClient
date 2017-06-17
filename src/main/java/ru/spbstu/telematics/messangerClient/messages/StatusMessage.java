package ru.spbstu.telematics.messangerClient.messages;

import lombok.Getter;
import ru.spbstu.telematics.messangerClient.store.User;

/**
 * Created by ihb on 17.06.17.
 */

@Getter
public class StatusMessage extends Message {

    public static final int STATUS_OK = -1;

    public static final int LOGIN_ERROR = 1;

    private final int statusCode;

    User user;

    public StatusMessage(int statusCode) {
        setType(Type.MSG_STATUS);

        this.statusCode = statusCode;
    }


    public StatusMessage(int statusCode, User user) {
        setType(Type.MSG_STATUS);

        this.statusCode = statusCode;
    }
}
