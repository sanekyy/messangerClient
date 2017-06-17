package ru.spbstu.telematics.messangerClient.logic.commands;

import ru.spbstu.telematics.messangerClient.exceptions.CommandException;
import ru.spbstu.telematics.messangerClient.messages.Message;
import ru.spbstu.telematics.messangerClient.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public class Login implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

    }
}
