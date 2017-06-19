package ru.spbstu.telematics.messengerClient.logic.commands;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.InfoResultMessage;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;
import ru.spbstu.telematics.messengerClient.store.User;

/**
 * Created by ihb on 19.06.17.
 */
public class InfoResult implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        InfoResultMessage infoResultMessage = (InfoResultMessage) message;

        switch (infoResultMessage.getStatusCode()) {
            case InfoResultMessage.STATUS_OK:
                User user = infoResultMessage.getUser();
                System.out.println("id = " + user.getId() + "\n" +
                        "login = " + user.getLogin() + "\n");
                break;
            case InfoResultMessage.PERMISSION_DENIED:
                System.out.println("Permission denied");
                break;
            case InfoResultMessage.USER_NOT_FOUND:
                System.out.println("User not found");
                break;
            default:
                throw new CommandException("Status unknown");
        }


    }
}
