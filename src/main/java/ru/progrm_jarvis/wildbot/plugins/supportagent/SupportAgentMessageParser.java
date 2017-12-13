package ru.progrm_jarvis.wildbot.plugins.supportagent;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.utils.ShortLink;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import ru.wildbot.core.WildBotCore;
import ru.wildbot.core.console.logging.Tracer;

import java.util.Arrays;

@RequiredArgsConstructor
public class SupportAgentMessageParser {
    @NonNull @Getter private final SupportAgentPlugin plugin;

    public final void parse(final Message message) throws ApiException, ClientException {
        if (message.getBody().startsWith("!") || message.getBody().startsWith("/")) {
            parseCommand(message);
        }
    }

    public final void parseCommand(final Message message) throws ApiException, ClientException {
        val command = message.getBody().contains(" ")
                ? message.getBody().substring(1, message.getBody().indexOf(' '))
                : message.getBody().substring(1);
        val args = message.getBody().contains(" ")
                ? message.getBody().substring(message.getBody().indexOf(' ') + 1).split("\\s")
                : new String[0];

        switch (command) {
            case "help": case "support": case "помощь": {
                if (args.length < 1) {
                    WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                            .userId(message.getUserId())
                            .message(plugin.getMessages().getHelpCommandUsage())
                            .execute();
                    return;
                } else {
                    if (plugin.getSettings().getSupportAgentsIds().size() < 1) {
                        WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                                .userId(message.getUserId())
                                .message(plugin.getMessages().getNoSupportAgent())
                                .execute();
                        return;
                    }
                    val helpMessage = new StringBuilder();
                    for (int i = 0; i < args.length; i++) helpMessage.append(args[i])
                            .append(i < args.length - 1 ? " " : "");

                    WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                            .userId(plugin.getSettings().getSupportAgentsIds()
                                    .get((int) (Math.random() * plugin.getSettings().getSupportAgentsIds().size())))
                            .message(String.format(plugin.getMessages().getIncomingHelpRequest(), message.getUserId(),
                                    helpMessage))
                            .execute();

                    WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                            .userId(message.getUserId())
                            .message(plugin.getMessages().getSentHelpRequest())
                            .execute();
                }
                return;
            }
            case "helpers": case "агенты": {
                val supportAgents = new StringBuilder();
                for (int i = 0; i < plugin.getSettings().getSupportAgentsIds().size(); i++) supportAgents
                        .append("[id")
                        .append(plugin.getSettings().getSupportAgentsIds().get(i))
                        .append("|Агент #")
                        .append(i + 1)
                        .append(i < args.length - 1 ? "], " : "]");

                WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                        .userId(message.getUserId())
                        .message(String.format(plugin.getMessages().getSupportAgents(), supportAgents))
                        .execute();
                return;
            }
            case "commands": case "команды": {
                WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                        .userId(message.getUserId())
                        .message(plugin.getMessages().getCommands())
                        .execute();
                return;
            }
            case "shorten": case "short": case "сократи": {
                if (args.length < 1) {
                    WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                            .userId(message.getUserId())
                            .message(plugin.getMessages().getShortenCommandUsage())
                            .execute();
                    return;
                }

                final ShortLink shortLink;
                try {
                    shortLink = WildBotCore.vkApi().utils()
                            .getShortLink(WildBotCore.vkApiManager().getActor(), args[0])
                            .execute();
                } catch (ApiException e) {
                    if (e.getCode() == 100) {
                        WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                                .userId(message.getUserId())
                                .message(plugin.getMessages().getNotALink())
                                .execute();

                        return;
                    } else throw new ApiException(e.getCode(), e.getDescription(), e.getMessage());
                }

                WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                        .userId(message.getUserId())
                        .message(String.format(plugin.getMessages().getShortLink(), shortLink.getShortUrl()))
                        .execute();
                return;
            }
            default: {
                WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                        .userId(message.getUserId())
                        .message(plugin.getMessages().getUnknownCommand())
                        .execute();
            }
        }
    }
}
