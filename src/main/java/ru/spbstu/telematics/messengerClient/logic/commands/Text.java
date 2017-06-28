package ru.spbstu.telematics.messengerClient.logic.commands;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.TextMessage;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 23.06.17.
 */
public class Text implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        TextMessage textMessage = (TextMessage) message;
        System.out.println("Message from user " + textMessage.getSenderId() + " in " + textMessage.getTimestamp() + ": " + textMessage.getText());
    }
}
