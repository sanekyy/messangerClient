package ru.spbstu.telematics.messengerClient.network;


import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.ProtocolException;

/**
 *
 */
public interface IProtocol {

    Message decode(byte[] bytes) throws ProtocolException;

    byte[] encode(Message msg) throws ProtocolException;

}
