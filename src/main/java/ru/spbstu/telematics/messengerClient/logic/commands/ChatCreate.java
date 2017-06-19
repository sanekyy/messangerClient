package ru.spbstu.telematics.messengerClient.logic.commands;


import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatCreate implements ICommand {

    @Override
    public void execute(Session ISession, Message message) throws CommandException {

    }
}
