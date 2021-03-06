package ru.spbstu.telematics.messengerClient.network;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.*;
import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message.Type;
import ru.spbstu.telematics.messengerClient.exceptions.ProtocolException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Простейший протокол передачи данных
 */
public class StringProtocol implements IProtocol {

    public static final String DELIMITER = ";";

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        String rawData = new String(bytes).trim();
        System.out.println("RECEIVE: \n" + rawData + "\n");

        Pattern groupIdPattern = Pattern.compile(DELIMITER);
        Matcher matcher = groupIdPattern.matcher(rawData);

        if(!matcher.find()){
            throw new ProtocolException("Delimiter doesn't found");
        }

        int startPos = matcher.start();

        Type type = Type.valueOf(rawData.substring(0,startPos));
        rawData = rawData.substring(startPos+1);

        switch (type) {
            case MSG_TEXT:
                return new Gson().fromJson(rawData, new TypeToken<TextMessage>(){}.getType());
            case MSG_STATUS:
                return new Gson().fromJson(rawData, new TypeToken<StatusMessage>() {
                }.getType());
            case MSG_INFO_RESULT:
                return new Gson().fromJson(rawData, new TypeToken<InfoResultMessage>() {
                }.getType());
            case MSG_CHAT_LIST_RESULT:
                return new Gson().fromJson(rawData, new TypeToken<ChatListResultMessage>() {
                }.getType());
            case MSG_CHAT_HIST_RESULT:
                return new Gson().fromJson(rawData, new TypeToken<ChatHistResultMessage>() {
                }.getType());
            default:
                throw new ProtocolException("Invalid type: " + type);
        }
    }

    @Override
    public byte[] encode(Message message) throws ProtocolException {
        StringBuilder builder = new StringBuilder();
        Type type = message.getType();
        builder.append(type).append(DELIMITER);
        switch (type) {
            case MSG_REGISTRATION:
            case MSG_LOGIN:
            case MSG_STATUS:
            case MSG_INFO:
            case MSG_CHAT_LIST:
            case MSG_CHAT_CREATE:
            case MSG_CHAT_HIST:
            case MSG_TEXT:
                builder.append(new Gson().toJson(message));
                break;
            default:
                throw new ProtocolException("Invalid type: " + type);


        }
        System.out.println("SEND: \n" + builder + "\n");
        return builder.toString().getBytes();
    }

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            // who care
        }
        return null;
    }
}