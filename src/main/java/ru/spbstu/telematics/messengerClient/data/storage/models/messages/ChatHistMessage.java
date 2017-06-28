package ru.spbstu.telematics.messengerClient.data.storage.models.messages;


/**
 * Created by ihb on 20.06.17.
 */
public class ChatHistMessage extends Message {

    Long chatId;

    public ChatHistMessage(Long chatId) {
        setType(Type.MSG_CHAT_HIST);

        this.chatId = chatId;
    }
}
