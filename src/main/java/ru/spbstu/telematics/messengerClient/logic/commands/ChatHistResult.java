package ru.spbstu.telematics.messengerClient.logic.commands;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.ChatHistResultMessage;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 20.06.17.
 */
public class ChatHistResult implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        ChatHistResultMessage chatHistResultMessage = (ChatHistResultMessage) message;

        if (chatHistResultMessage.getMessages() == null || chatHistResultMessage.getMessages().size() == 0) {
            System.out.println("No messages");
        } else {
            chatHistResultMessage.getMessages().forEach(textMessage -> {
                System.out.println(textMessage.getSenderId() + " in " + textMessage.getTimestamp() + " : " + textMessage.getText());
            });
        }
    }
}
