package ru.spbstu.telematics.messangerClient.logic.commands;

import ru.spbstu.telematics.messangerClient.exceptions.CommandException;
import ru.spbstu.telematics.messangerClient.messages.Message;
import ru.spbstu.telematics.messangerClient.messages.StatusMessage;
import ru.spbstu.telematics.messangerClient.network.Session;

/**
 * Created by ihb on 17.06.17.
 */
public class Status implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        StatusMessage statusMessage = (StatusMessage) message;


        switch (statusMessage.getStatusCode()) {
            case StatusMessage.LOGIN_ERROR:
                System.out.println("Login error");
                break;
            case StatusMessage.STATUS_OK:
                System.out.print("Login success");
                break;
            default:
                throw new CommandException("StatusCommand Code unknown");
        }
    }
}
