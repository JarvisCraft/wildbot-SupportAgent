package ru.progrm_jarvis.wildbot.plugins.supportagent;

import lombok.*;
import ru.wildbot.core.data.json.AbstractJsonData;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupportAgentMessages extends AbstractJsonData {
    @NonNull private String unknownCommand = "Неизвестная команда";
    @NonNull private String helpCommandUsage = "Для использования команды укажите также сообщение с описанием проблемы";
    @NonNull private String shortenCommandUsage = "Для использования команды укажите также ссылку для сокращения";
    @NonNull private String noSupportAgent = "Отсутсвует агент поддержки";
    @NonNull private String sentHelpRequest = "Запрос помощи отправлен";
    @NonNull private String commands = "Доступные команды:" +
            "\n/help <сообщение> -- обратиться за помощью к агенту тех. поддержки" +
            "\n/helpers -- список агентов тех. поддержки" +
            "\n/shorten <ссылка> -- сократить ссылку до вида `vk.cc/foobar`" +
            "\n/commands -- список доступных команд";
    @NonNull private String incomingHelpRequest = "Поступил запрос о помощи от [id%d|пользователя]:%n%s";
    @NonNull private String onJoin = "Добро пожаловать";
    @NonNull private String onLeave = "Надеемся, что Вы ещё вернётесь";
    @NonNull private String supportAgents = "Агенты поддержки: %s";
    @NonNull private String info = "Агенты поддержки: %s";
    @NonNull private String shortLink = "Сокращённая ссылка:%n%s";
    @NonNull private String notALink = "Предоставленный Вами текст не является ссылкой";
}
