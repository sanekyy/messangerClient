package ru.spbstu.telematics.messangerClient.logic;


import ru.spbstu.telematics.messangerClient.exceptions.CommandException;
import ru.spbstu.telematics.messangerClient.logic.commands.ICommand;
import ru.spbstu.telematics.messangerClient.logic.commands.Status;
import ru.spbstu.telematics.messangerClient.messages.Message;
import ru.spbstu.telematics.messangerClient.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public class CommandHandler {

    private static ICommand status = new Status();


    public static void status(Session session, Message message){
        try {
            status.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
