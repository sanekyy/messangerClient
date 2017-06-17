package ru.spbstu.telematics.messangerClient.messages;

import lombok.Getter;

/**
 * Created by ihb on 14.06.17.
 */

@Getter
public class LoginMessage extends Message {

    public String login;
    public String password;

    public LoginMessage(String login, String password) {
        setType(Type.MSG_LOGIN);

        this.login = login;
        this.password = password;
    }
}
