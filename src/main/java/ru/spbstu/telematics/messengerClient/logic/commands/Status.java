package ru.spbstu.telematics.messengerClient.logic.commands;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.StatusMessage;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 17.06.17.
 */
public class Status implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        StatusMessage statusMessage = (StatusMessage) message;

        session.setUser(statusMessage.getUser());

        switch (statusMessage.getStatusCode()) {
            case StatusMessage.LOGIN_SUCCESS:
                System.out.println("Login success");
                session.setUser(statusMessage.getUser());
                break;
            case StatusMessage.REGISTRATION_SUCCESS:
                System.out.println("Registration success");
                break;
            case StatusMessage.REGISTRATION_ERROR:
                System.out.println("Registration failed, login is busy");
                break;
            case StatusMessage.LOGIN_ERROR:
                System.out.println("Login error");
                break;

            default:
                throw new CommandException("StatusCommand Code unknown");
        }
    }
}
