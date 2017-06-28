package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

/**
 * Created by ihb on 20.06.17.
 */
public class ChatListMessage extends Message {

    public ChatListMessage() {
        setType(Type.MSG_CHAT_LIST);
    }
}
