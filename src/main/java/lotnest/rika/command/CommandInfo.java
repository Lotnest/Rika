package lotnest.rika.command;

import lombok.Data;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

@Data
public class CommandInfo {

    private final Guild guild;
    private final Member executor;
    private final Message message;
    private final MessageChannel channel;
    private final String[] args;
}
