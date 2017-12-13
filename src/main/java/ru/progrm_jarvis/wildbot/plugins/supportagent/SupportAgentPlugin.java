package ru.progrm_jarvis.wildbot.plugins.supportagent;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import ru.wildbot.core.WildBotCore;
import ru.wildbot.core.api.plugin.WildBotJavaPlugin;
import ru.wildbot.core.api.plugin.annotation.OnEnable;
import ru.wildbot.core.api.plugin.annotation.WildBotPluginData;
import ru.wildbot.core.console.logging.AnsiCodes;
import ru.wildbot.core.data.json.JsonDataManager;
import ru.wildbot.core.data.json.JsonNotPresentException;
import ru.wildbot.core.vk.callback.event.VkGroupJoinEvent;
import ru.wildbot.core.vk.callback.event.VkGroupLeaveEvent;
import ru.wildbot.core.vk.callback.event.VkMessageNewEvent;

@WildBotPluginData(name = "SupportAgent", version = "1.0", authors = "JARvis (Пётр) PROgrammer")
@Log4j2(topic = "SupportAgent")
public class SupportAgentPlugin extends WildBotJavaPlugin {
    @NonNull @Getter private SupportAgentEventListener eventListener;
    @NonNull @Getter private SupportAgentMessageParser commandParser;
    @NonNull @Getter private SupportAgentSettings settings;
    @NonNull @Getter private SupportAgentMessages messages;

    @OnEnable public void onEnable() {
        log.info(AnsiCodes.BG_BLUE + "Enabling SupportAgent plugin" + AnsiCodes.RESET);

        eventListener = new SupportAgentEventListener(this);
        WildBotCore.eventManager().registerListeners(VkMessageNewEvent.class, eventListener);
        WildBotCore.eventManager().registerListeners(VkGroupJoinEvent.class, eventListener);
        WildBotCore.eventManager().registerListeners(VkGroupLeaveEvent.class, eventListener);

        commandParser = new SupportAgentMessageParser(this);

        try {
            messages = JsonDataManager
                    .readAndWrite("plugins/support-agent/messages.json", SupportAgentMessages.class, true)
                    .orElseThrow(JsonNotPresentException::new);
        } catch (JsonNotPresentException e) {
            log.error("An exception occurred while trying to load messages");
            e.printStackTrace();
        }

        try {
            settings = JsonDataManager
                    .readAndWrite("plugins/support-agent/settings.json", SupportAgentSettings.class, true)
                    .orElseThrow(JsonNotPresentException::new);
        } catch (JsonNotPresentException e) {
            log.error("An exception occurred while trying to load messages");
            e.printStackTrace();
        }

        log.info(AnsiCodes.BG_GREEN + "SupportAgent plugin has been successfully enabled" + AnsiCodes.RESET);
    }

}
