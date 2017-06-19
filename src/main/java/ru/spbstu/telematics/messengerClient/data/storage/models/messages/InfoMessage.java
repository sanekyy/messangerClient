package ru.spbstu.telematics.messengerClient.data.storage.models.messages;

/**
 * Created by ihb on 17.06.17.
 */

public class InfoMessage extends Message {

    Long requiredId;


    public InfoMessage(Long requiredId) {
        setType(Type.MSG_INFO);
        this.requiredId = requiredId;
    }
}
