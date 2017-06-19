package ru.spbstu.telematics.messengerClient.logic;


import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.logic.commands.ICommand;
import ru.spbstu.telematics.messengerClient.logic.commands.InfoResult;
import ru.spbstu.telematics.messengerClient.logic.commands.Status;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public class CommandHandler {

    private static ICommand status = new Status();
    private static ICommand infoResult = new InfoResult();


    public static void status(Session session, Message message){
        try {
            status.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void infoResult(Session session, Message message) {
        try {
            infoResult.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
