package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import java.util.List;

/**
 * Created by ihb on 20.06.17.
 */
public class ChatCreateMessage extends Message {

    List<Long> participants;

    public ChatCreateMessage(List<Long> participants) {
        setType(Type.MSG_CHAT_CREATE);

        this.participants = participants;
    }
}
