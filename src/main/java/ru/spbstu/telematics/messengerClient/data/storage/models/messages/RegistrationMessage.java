package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

/**
 * Created by ihb on 18.06.17.
 */
public class RegistrationMessage extends Message {

    String login;
    String password;

    public RegistrationMessage(String login, String password) {
        setType(Type.MSG_REGISTRATION);

        this.login = login;
        this.password = password;
    }
}
