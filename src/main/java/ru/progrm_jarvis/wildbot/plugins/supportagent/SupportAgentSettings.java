package ru.progrm_jarvis.wildbot.plugins.supportagent;

import lombok.*;
import ru.wildbot.core.data.json.AbstractJsonData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupportAgentSettings extends AbstractJsonData {
    @NonNull private List<Integer> supportAgentsIds = Collections.singletonList(288451376);
}
