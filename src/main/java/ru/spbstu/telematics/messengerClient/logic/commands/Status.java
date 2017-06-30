package ru.spbstu.telematics.messengerClient.logic.commands;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.StatusMessage;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;


public class Status implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        StatusMessage statusMessage = (StatusMessage) message;

        if (statusMessage.getUser() != null) {
            session.setUser(statusMessage.getUser());
        }

        switch (statusMessage.getStatusCode()) {
            case StatusMessage.LOGIN_SUCCESS:
                System.out.println("Login success");
                session.setUser(statusMessage.getUser());
                break;
            case StatusMessage.REGISTRATION_SUCCESS:
                System.out.println("Registration success");
                break;
            case StatusMessage.CHAT_CREATE_SUCCESS:
                System.out.println("Chat successfully created");
                break;
            case StatusMessage.TEXT_MESSAGE_SUCCESS:
                System.out.println("Message successfully sent");
                break;
            case StatusMessage.LOGIN_ERROR:
                System.out.println("Login error");
                break;
            case StatusMessage.REGISTRATION_ERROR:
                System.out.println("Registration failed, login is busy");
                break;
            case StatusMessage.CHAT_CREATE_ERROR:
                System.out.println("Chat creation failed");
                break;
            case StatusMessage.USER_NOT_EXIST_ERROR:
                System.out.println("User doesn't exist");
                break;
            case StatusMessage.SERVER_ERROR:
                System.out.println("Server error");
                break;
            case StatusMessage.CHAT_NOT_EXIST:
                System.out.println("Chat doesn't exist");
                break;
            case StatusMessage.MESSAGE_TOO_LONG:
                System.out.println("Message too long");
                break;
            case StatusMessage.PERMISSION_DENIED:
                System.out.println("Permission denied");
                break;

            default:
                throw new CommandException("StatusCommand Code unknown");
        }
    }
}
