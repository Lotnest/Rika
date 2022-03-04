package dev.lotnest.rika.command.student;

import dev.lotnest.rika.command.AbstractCommand;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.MessageUtils;
import dev.lotnest.rika.utils.RoleUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupsCommand extends AbstractCommand {

    @Override
    public @NotNull String getName() {
        return CommandConstants.GROUPS;
    }

    @Override
    public @NotNull CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        Guild guild = commandInfo.getGuild();

        CompletableFuture.runAsync(() -> {
            AtomicInteger i = new AtomicInteger();

            embedBuilder.appendDescription("\n" + MessageConstants.MOST_POPULAR_EXERCISE_GROUPS);
            RoleUtils.findRolesWithMemberCount(GroupCommand.EXERCISE_PATTERN, guild)
                    .forEach((role, memberCount) -> embedBuilder.appendDescription("\n[" + i.incrementAndGet() + "] " + role.getName() + ": " + memberCount));

            i.set(0);
            embedBuilder.appendDescription("\n\n");

            embedBuilder.appendDescription(MessageConstants.MOST_POPULAR_LANGUAGE_GROUPS);
            RoleUtils.findRolesWithMemberCount(GroupCommand.LANGUAGE_PATTERN, guild)
                    .forEach((role, memberCount) -> embedBuilder.appendDescription("\n[" + i.incrementAndGet() + "] " + role.getName() + ": " + memberCount));

            commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            commandInfo.getMessage().delete().queue();
        });
    }
}
