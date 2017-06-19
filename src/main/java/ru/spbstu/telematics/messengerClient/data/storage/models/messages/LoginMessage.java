package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import lombok.Getter;

/**
 * Created by ihb on 14.06.17.
 */

@Getter
public class LoginMessage extends Message {

    String login;
    String password;

    public LoginMessage(String login, String password) {
        setType(Type.MSG_LOGIN);

        this.login = login;
        this.password = password;
    }
}
