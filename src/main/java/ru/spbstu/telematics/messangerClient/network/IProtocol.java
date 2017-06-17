package ru.spbstu.telematics.messangerClient.network;


import ru.spbstu.telematics.messangerClient.exceptions.ProtocolException;
import ru.spbstu.telematics.messangerClient.messages.Message;

/**
 *
 */
public interface IProtocol {

    Message decode(byte[] bytes) throws ProtocolException;

    byte[] encode(Message msg) throws ProtocolException;

}
