package ru.spbstu.telematics.messangerClient.logic.commands;


import ru.spbstu.telematics.messangerClient.exceptions.CommandException;
import ru.spbstu.telematics.messangerClient.messages.Message;
import ru.spbstu.telematics.messangerClient.network.Session;

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
