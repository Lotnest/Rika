package dev.lotnest.rika.command;

import lombok.Data;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

@Data
public class CommandInfo {

    private final @NotNull Guild guild;
    private final @NotNull Member executor;
    private final @NotNull Message message;
    private final @NotNull MessageChannel channel;
    private final @NotNull String[] args;
}
