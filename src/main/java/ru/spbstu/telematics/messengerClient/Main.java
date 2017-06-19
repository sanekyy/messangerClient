package ru.spbstu.telematics.messengerClient;

import ru.spbstu.telematics.messengerClient.data.storage.models.messages.*;
import ru.spbstu.telematics.messengerClient.exceptions.ProtocolException;
import ru.spbstu.telematics.messengerClient.network.IProtocol;
import ru.spbstu.telematics.messengerClient.network.Session;
import ru.spbstu.telematics.messengerClient.network.StringProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ihb on 17.06.17.
 */
public class Main {

    private static IProtocol protocol = new StringProtocol();
    private static Session session;


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
            System.err.println("Application failed. " + e);
        } finally {
            // TODO: 17.06.17  close();
        }
    }

    private static void initSocket() throws IOException {
        InetSocketAddress address = new InetSocketAddress(AppConfig.HOST, AppConfig.PORT);
        SocketChannel socketChannel = SocketChannel.open(address);

        session = new Session(socketChannel.socket());

        Thread socketListenerThread = new Thread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(AppConfig.BUFFER_SIZE);

            System.out.println("Starting listener thread...");

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int read = socketChannel.read(buffer);
                    if (read > 0) {
                        Message message = protocol.decode(Arrays.copyOf(buffer.array(), read));
                        session.onMessage(message);
                    }
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
     * Реагируем на входящее сообщение
     */

    /**
     * Обрабатывает входящую строку, полученную с консоли
     * Формат строки можно посмотреть в вики проекта
     */
    private static void processInput(String rawData) throws IOException, ProtocolException {

        String[] tokens;
        Message message;

        Pattern groupIdPattern = Pattern.compile(" |$");
        Matcher matcher = groupIdPattern.matcher(rawData);

        if(!matcher.find()){
            throw new ProtocolException("Delimiter doesn't found.");
        }

        int startPos = matcher.start();

        String command = rawData.substring(0,startPos);

        switch (command) {
            case "/registration":
                if (session.isLoggedIn()) {
                    System.out.println("You are logged in. Please logout to register.");
                    return;
                }

                rawData = rawData.substring(startPos + 1);
                tokens = rawData.split(" ");
                if (tokens.length != 2) {
                    System.out.println("Parameters Error.");
                    return;
                }

                message = new RegistrationMessage(tokens[0], tokens[1]);
                session.send(message);
                break;
            case "/login":
                if (session.isLoggedIn()) {
                    System.out.println("Already logged in.");
                    return;
                }

                rawData = rawData.substring(startPos + 1);
                tokens = rawData.split(" ");
                if(tokens.length != 2){
                    System.out.println("Parameters Error.");
                    return;
                }

                message = new LoginMessage(tokens[0], tokens[1]);
                session.send(message);
                break;
            case "/logout":
                if (!session.isLoggedIn()) {
                    System.out.println("Already logouted.");
                    return;
                }
                session.logout();
                System.out.println("Logout success");
                break;
            case "/info":
                if (!session.isLoggedIn()) {
                    System.out.println("Error, you are not logged in. Please login or register.");
                    return;
                }

                if (rawData.length() > "/info".length()) {
                    rawData = rawData.substring(startPos + 1);
                    tokens = rawData.split(" ");
                    if (tokens.length != 1) {
                        System.out.println("Parameters Error.");
                        return;
                    }

                    message = new InfoMessage(Long.valueOf(tokens[0]));
                } else {
                    message = new InfoMessage(session.getUser().getId());
                }
                session.send(message);
                break;
            case "/help":
                Utils.printHelp();
                break;
            case "/text":
                if (!session.isLoggedIn()) {
                    System.out.println("Error, you are not logged in. Please login or register.");
                    return;
                }

                rawData = rawData.substring(startPos + 1);

                session.send(new TextMessage(rawData));
                break;
            default:
                System.err.println("Invalid input: " + rawData);
        }
    }

}
