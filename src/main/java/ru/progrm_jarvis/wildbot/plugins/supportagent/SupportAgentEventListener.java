package ru.progrm_jarvis.wildbot.plugins.supportagent;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.wildbot.core.WildBotCore;
import ru.wildbot.core.api.event.EventHandler;
import ru.wildbot.core.vk.callback.event.VkGroupJoinEvent;
import ru.wildbot.core.vk.callback.event.VkGroupLeaveEvent;
import ru.wildbot.core.vk.callback.event.VkMessageNewEvent;

@RequiredArgsConstructor
public class SupportAgentEventListener {
    @NonNull @Getter private final SupportAgentPlugin plugin;

    @EventHandler public void onVkMessageNew(final VkMessageNewEvent event) {
        try {
            plugin.getCommandParser().parse(event.getMessage());
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    @EventHandler public void onVkGroupJoin(final VkGroupJoinEvent event) {
        try {
            WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                    .userId(event.getMessage().getUserId())
                    .message(plugin.getMessages().getOnJoin())
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    @EventHandler public void onVkGroupLeave(final VkGroupLeaveEvent event) {
        try {
            WildBotCore.vkApi().messages().send(WildBotCore.vkApiManager().getActor())
                    .userId(event.getMessage().getUserId())
                    .message(plugin.getMessages().getOnLeave())
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
