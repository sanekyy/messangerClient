package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import lombok.Getter;
import ru.spbstu.telematics.messengerClient.store.User;

/**
 * Created by ihb on 19.06.17.
 */

@Getter
public class InfoResultMessage extends Message {

    public static final int STATUS_OK = -1;

    public static final int USER_NOT_FOUND = 1;

    public static final int PERMISSION_DENIED = 2;

    int statusCode;

    User user;
}
