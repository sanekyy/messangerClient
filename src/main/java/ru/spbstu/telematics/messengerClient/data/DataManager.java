package ru.spbstu.telematics.messengerClient.data;

import ru.spbstu.telematics.messengerClient.network.IProtocol;
import ru.spbstu.telematics.messengerClient.network.StringProtocol;


public class DataManager {

    private static DataManager INSTANCE = new DataManager();

    private IProtocol protocol = new StringProtocol();

    private DataManager() {

    }

    public static DataManager getInstance() {
        return INSTANCE;
    }

    public IProtocol getProtocol() {
        return protocol;
    }
}
