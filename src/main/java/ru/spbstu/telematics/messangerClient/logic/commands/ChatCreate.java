package ru.spbstu.telematics.messangerClient.logic.commands;


import ru.spbstu.telematics.messangerClient.exceptions.CommandException;
import ru.spbstu.telematics.messangerClient.messages.Message;
import ru.spbstu.telematics.messangerClient.network.Session;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatCreate implements ICommand {

    @Override
    public void execute(Session ISession, Message message) throws CommandException {

    }
}
