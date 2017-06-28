package ru.spbstu.telematics.messengerClient.logic;


import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.logic.commands.*;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public class CommandHandler {

    private static ICommand text = new Text();
    private static ICommand status = new Status();
    private static ICommand infoResult = new InfoResult();
    private static ICommand chatListResult = new ChatListResult();
    private static ICommand chatHistResult = new ChatHistResult();


    public static void text(Session session, Message message) {
        try {
            text.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

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

    public static void chatListResult(Session session, Message message) {
        try {
            chatListResult.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void chatHistResult(Session session, Message message) {
        try {
            chatHistResult.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
