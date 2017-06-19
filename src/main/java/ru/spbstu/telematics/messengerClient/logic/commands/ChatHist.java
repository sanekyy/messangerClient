package ru.spbstu.telematics.messengerClient.logic.commands;


import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatHist implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

    }
}
