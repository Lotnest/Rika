package lotnest.rika.reaction;

import lombok.Data;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

@Data
public class ReactionInfo {

    private final Guild guild;
    private final Member memberReacted;
    private final String messageId;
    private final String emoji;
    private final MessageChannel channel;
}
