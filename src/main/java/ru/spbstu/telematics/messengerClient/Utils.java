package ru.spbstu.telematics.messengerClient;


class Utils {

    static void printHelp() {
        printRegistrationHelp();

        printLoginHelp();

        printLogoutHelp();

        printInfoHelp();

        printChatCreateHelp();

        printTextHelp();

        printChatHistoryHelp();

        printChatListHelp();
    }

    static void printRegistrationHelp() {
        System.out.println("/registration [login] [password] \n" +
                "Registration you on server. If login is busy you'll get error. \n" +
                "Example: /registration sanekyy supersecret123 \n");
    }

    static void printLoginHelp() {
        System.out.println("/login [login] [password] \n" +
                "Login you on server. If login or password is incorrect you'll get error. \n" +
                "Example: /login sanekyy supersecret123 \n");
    }

    static void printLogoutHelp() {
        System.out.println("/logout [login] [password] \n" +
                "Log out you. \n" +
                "Example: /logout \n");
    }

    static void printInfoHelp() {
        System.out.println("/info [user_id] \n" +
                "Show info about user with given id. Without id - show info about current user. \n" +
                "If user with given id is absent you'll get error" +
                "Available only for logged in user. \n" +
                "Example: /info 2 \n");
    }

    static void printChatCreateHelp() {
        System.out.println("/chat_create [user_id list] \n" +
                "Create new chat and return chat id. If this is a chat with one user, then return existing chat, else create it. \n" +
                "If this is a multi-chat, than always create new chat. \n" +
                "Example: /chat_create 1, /chat_create 4 10 20 \n");
    }

    static void printTextHelp() {
        System.out.println("/text [chat_id] [message] \n" +
                "Send message in chat with given id. \n" +
                "If you don't have access to this chat, get an error. \n" +
                "Example: /text 5 Hello Max! \n");
    }

    static void printChatHistoryHelp() {
        System.out.println("/chat_history [chat_id] \n" +
                "Return chat history for chat with given id. \n" +
                "If you don't have access to this chat, get an error. \n" +
                "Example: /chat_history 10 \n");
    }

    static void printChatListHelp() {
        System.out.println("/chat_list \n" +
                "Return your chat list \n" +
                "Example /chat_list \n");
    }
}
