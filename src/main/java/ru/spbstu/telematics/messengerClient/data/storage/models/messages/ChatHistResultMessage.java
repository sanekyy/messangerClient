package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

import lombok.Getter;

import java.util.List;

/**
 * Created by ihb on 20.06.17.
 */
@Getter
public class ChatHistResultMessage extends Message {

    List<TextMessage> messages;
}
