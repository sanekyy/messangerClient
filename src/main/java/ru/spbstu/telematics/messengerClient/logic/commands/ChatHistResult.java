package ru.spbstu.telematics.messengerClient.logic.commands;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.ChatHistResultMessage;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;


public class ChatHistResult implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        ChatHistResultMessage chatHistResultMessage = (ChatHistResultMessage) message;

        switch (chatHistResultMessage.getStatusCode()) {
            case ChatHistResultMessage.CHAT_NOT_EXIST:
                System.out.println("Chat doesn't exist");
                break;
            case ChatHistResultMessage.PERMISSION_DENIED_ERROR:
                System.out.println("Permission denied");
                break;
            case ChatHistResultMessage.STATUS_SUCCESS:
                if (chatHistResultMessage.getMessages() == null || chatHistResultMessage.getMessages().size() == 0) {
                    System.out.println("No messages");
                } else {
                    chatHistResultMessage.getMessages().forEach(textMessage ->
                            System.out.println(textMessage.getSenderId() + " in " + textMessage.getTimestamp() + " : " + textMessage.getText()));
                }
                break;
            default:
                System.out.println("Unknown status");
        }


    }
}
