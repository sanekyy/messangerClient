package ru.spbstu.telematics.messengerClient.logic.commands;


import ru.spbstu.telematics.messengerClient.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerClient.exceptions.CommandException;
import ru.spbstu.telematics.messengerClient.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public interface ICommand {

    /**
     * Реализация паттерна Команда. Метод execute() вызывает соответствующую реализацию,
     * для запуска команды нужна сессия, чтобы можно было сгенерить ответ клиенту и провести валидацию
     * сессии.
     * @param session - текущая сессия
     * @param message - сообщение для обработки
     * @throws CommandException - все исключения перебрасываются как CommandException
     */
    void execute(Session session, Message message) throws CommandException;
}
