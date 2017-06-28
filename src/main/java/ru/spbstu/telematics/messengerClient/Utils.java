package ru.spbstu.telematics.messengerClient;

/**
 * Created by ihb on 18.06.17.
 */
public class Utils {

    public static void printHelp() {
        // TODO: 20.06.17 separate on independent function

        System.out.println("/registration [login] [password] \n" +
                "Registration you on server. If login is busy you'll get error. \n" +
                "Example: /registration sanekyy supersecret123");

        System.out.println("/login [login] [password] \n" +
                "Login you on server. If login or password is incorrect you'll get error. \n" +
                "Example: /login sanekyy supersecret123");

        System.out.println("/logout [login] [password] \n" +
                "Log out you. \n" +
                "Example: /logout");

        System.out.println("/info [id] \n" +
                "Show info about user with given id. Without id - show info about current user. \n" +
                "If user with given id is absent you'll get error" +
                "Available only for logged in user.");
    }
}
