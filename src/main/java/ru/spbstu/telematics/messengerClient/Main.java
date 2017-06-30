package ru.spbstu.telematics.messengerClient;

import ru.spbstu.telematics.messengerClient.data.DataManager;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.*;
import ru.spbstu.telematics.messengerClient.exceptions.ProtocolException;
import ru.spbstu.telematics.messengerClient.network.IProtocol;
import ru.spbstu.telematics.messengerClient.network.Session;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main {

    private static IProtocol protocol = DataManager.getInstance().getProtocol();
    private static Session session;
    private static Thread socketListenerThread;


    public static void main(String[] args) throws Exception {
        try {
            initSocket();


            // Цикл чтения с консоли
            Scanner scanner = new Scanner(System.in);
            System.out.println("$");
            while (true) {
                String input = scanner.nextLine().trim();
                if ("q".equals(input)) {
                    return;
                }
                try {
                    processInput(input);
                } catch (ProtocolException | IOException e) {
                    System.err.println("Failed to process user input " + e);
                }
            }
        } catch (Exception e) {
            if (AppConfig.DEBUG) {
                System.err.println("Application failed. " + e);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void initSocket() {
        InetSocketAddress address = new InetSocketAddress(AppConfig.HOST, AppConfig.PORT);
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open(address);
        } catch (IOException e) {
            if (e.toString().contains("Connection refused")) {
                System.out.println("Connection refused, to try reconnect enter /reconnect");
            } else {
                e.printStackTrace();
            }
            return;
        }

        while (socketChannel.isConnectionPending()) ;

        if (socketChannel.isConnected()) {
            System.out.println("Connection established");
        }

        session = new Session(socketChannel.socket());

        socketListenerThread = new Thread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(AppConfig.BUFFER_SIZE);

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int read = socketChannel.read(buffer);
                    if (read > 0) {
                        Message message = protocol.decode(Arrays.copyOf(buffer.array(), read));
                        session.onMessage(message);
                    } else if (read == -1) {
                        System.out.println("Connection lost, to try reconnect enter /reconnect");
                        session = null;
                        return;
                    }
                } catch (AsynchronousCloseException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Close connection");
                } catch (Exception e) {
                    System.err.println("Failed to process connection: " + e);
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } finally {
                    buffer.clear();
                }
            }
        });

        socketListenerThread.start();
    }

    /**
     * Обрабатывает входящую строку, полученную с консоли
     * Формат строки можно посмотреть в вики проекта
     */
    private static void processInput(String rawData) throws IOException, ProtocolException {

        String[] tokens;
        Long chatId;
        Message message;

        Pattern groupIdPattern = Pattern.compile(" |$");
        Matcher matcher = groupIdPattern.matcher(rawData);

        if(!matcher.find()){
            throw new ProtocolException("Delimiter doesn't found.");
        }

        int startPos = matcher.start();

        String command = rawData.substring(0,startPos);

        if (session == null && !"/reconnect".equals(command)) {
            System.out.println("Connection lost, try to reconnect ( /reconnect )");
            return;
        }

        switch (command) {
            case "/reconnect":
                initSocket();
                break;
            case "/registration":
                if (session.isLoggedIn()) {
                    System.out.println("You are logged in. Please logout to register.");
                    Utils.printLogoutHelp();
                    return;
                }

                if ("/registration".equals(rawData)) {
                    System.out.println("Parameters Error.");
                    Utils.printRegistrationHelp();
                    return;
                }

                rawData = rawData.substring(startPos + 1);
                tokens = rawData.split(" ");
                if (tokens.length != 2) {
                    System.out.println("Parameters Error.");
                    Utils.printRegistrationHelp();
                    return;
                }

                message = new RegistrationMessage(tokens[0], tokens[1]);
                session.send(message);
                break;
            case "/login":
                if (session.isLoggedIn()) {
                    System.out.println("Already logged in.");
                    Utils.printLogoutHelp();
                    return;
                }

                if ("/login".equals(rawData)) {
                    System.out.println("Parameters Error.");
                    Utils.printLoginHelp();
                    return;
                }

                rawData = rawData.substring(startPos + 1);
                tokens = rawData.split(" ");
                if(tokens.length != 2){
                    System.out.println("Parameters Error.");
                    Utils.printLoginHelp();
                    return;
                }

                message = new LoginMessage(tokens[0], tokens[1]);
                session.send(message);
                break;
            case "/logout":
                if (!session.isLoggedIn()) {
                    System.out.println("Already logouted.");
                    Utils.printLoginHelp();
                    return;
                }
                session.logout();
                System.out.println("Logout success");
                break;
            case "/info":
                if (!session.isLoggedIn()) {
                    System.out.println("Error, you are not logged in. Please login or register.");
                    Utils.printLoginHelp();
                    Utils.printRegistrationHelp();
                    return;
                }

                if (rawData.length() > "/info".length()) {
                    rawData = rawData.substring(startPos + 1);
                    tokens = rawData.split(" ");
                    if (tokens.length != 1) {
                        System.out.println("Parameters Error.");
                        Utils.printInfoHelp();
                        return;
                    }

                    Long userId;

                    try {
                        userId = Long.valueOf(tokens[0]);
                    } catch (NumberFormatException e) {
                        System.out.println("Parameters Error.");
                        Utils.printInfoHelp();
                        return;
                    }

                    message = new InfoMessage(userId);
                } else {
                    message = new InfoMessage(session.getUser().getId());
                }
                session.send(message);
                break;
            case "/chat_list":
                if (!session.isLoggedIn()) {
                    System.out.println("Error, you are not logged in. Please login or register.");
                    Utils.printLoginHelp();
                    Utils.printRegistrationHelp();
                    return;
                }

                if (rawData.length() > "/chat_list".length()) {
                    System.out.println("Parameters Error.");
                    Utils.printChatListHelp();
                    return;
                }

                message = new ChatListMessage();
                session.send(message);
                break;
            case "/chat_create":
                if (!session.isLoggedIn()) {
                    System.out.println("Error, you are not logged in. Please login or register.");
                    Utils.printLoginHelp();
                    Utils.printRegistrationHelp();
                    return;
                }

                if ("/chat_create".equals(rawData)) {
                    System.out.println("Parameters Error.");
                    Utils.printChatCreateHelp();
                    return;
                }


                rawData = rawData.substring(startPos + 1);
                tokens = rawData.split(" ");

                List<Long> participants;
                try {
                    participants = Arrays.stream(tokens).map(Long::valueOf).collect(Collectors.toList());
                    if (participants.contains(session.getUser().getId())) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("Parameters Error.");
                    Utils.printChatCreateHelp();
                    return;
                }

                participants.add(session.getUser().getId());

                message = new ChatCreateMessage(participants);
                session.send(message);
                break;
            case "/chat_history":
                if (!session.isLoggedIn()) {
                    System.out.println("Error, you are not logged in. Please login or register.");
                    Utils.printLoginHelp();
                    Utils.printRegistrationHelp();
                    return;
                }

                if ("/chat_history".equals(rawData)) {
                    System.out.println("Parameters Error.");
                    Utils.printChatHistoryHelp();
                    return;
                }

                rawData = rawData.substring(startPos + 1);
                tokens = rawData.split(" ");
                if (tokens.length != 1) {
                    System.out.println("Parameters Error.");
                    Utils.printChatHistoryHelp();
                    return;
                }

                try {
                    chatId = Long.valueOf(tokens[0]);
                } catch (NumberFormatException e) {
                    Utils.printChatHistoryHelp();
                    System.out.println("Parameters Error.");
                    return;
                }

                message = new ChatHistMessage(chatId);
                session.send(message);
                break;
            case "/text":
                if (!session.isLoggedIn()) {
                    System.out.println("Error, you are not logged in. Please login or register.");
                    Utils.printLoginHelp();
                    Utils.printRegistrationHelp();
                    return;
                }

                if ("/text".equals(rawData)) {
                    System.out.println("Parameters Error.");
                    Utils.printTextHelp();
                    return;
                }

                rawData = rawData.substring(startPos + 1);

                matcher = groupIdPattern.matcher(rawData);

                if (!matcher.find()) {
                    System.out.println("Parameters Error.");
                    Utils.printTextHelp();
                    return;
                }

                startPos = matcher.start();

                if (rawData.length() == startPos) {
                    System.out.println("Parameters Error.");
                    Utils.printTextHelp();
                    return;
                }

                try {
                    chatId = Long.valueOf(rawData.substring(0, startPos));
                } catch (NumberFormatException e) {
                    System.out.println("Parameters Error.");
                    Utils.printTextHelp();
                    return;
                }

                String text = rawData.substring(startPos + 1);

                message = new TextMessage(chatId, text);
                session.send(message);
                break;
            case "/help":
                Utils.printHelp();
                break;
            default:
                System.err.println("Invalid input: " + rawData);
        }
    }

}
