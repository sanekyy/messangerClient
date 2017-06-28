package ru.spbstu.telematics.messengerClient.logic.commands;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.ChatListResultMessage;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 20.06.17.
 */
public class ChatListResult implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        ChatListResultMessage chatListResultMessage = (ChatListResultMessage) message;

        if (chatListResultMessage.getChats() == null || chatListResultMessage.getChats().size() == 0) {
            System.out.println("You do not have any chats");
        } else {
            System.out.println("You are in the following chat rooms:");
            chatListResultMessage.getChats().forEach(System.out::println);
        }
    }
}
