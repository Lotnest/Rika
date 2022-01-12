package lotnest.rika.command.student;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.utils.MessageUtils;
import lotnest.rika.utils.RoleUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupsCommand extends Command {

    @Override
    public String getName() {
        return CommandProperty.GROUPS;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        Guild guild = commandInfo.getGuild();

        CompletableFuture.runAsync(() -> {
            AtomicInteger i = new AtomicInteger();

            embedBuilder.appendDescription("\n" + MessageProperty.MOST_POPULAR_EXERCISE_GROUPS);
            RoleUtil.findRolesWithMemberCount(GroupCommand.EXERCISE_PATTERN, guild)
                    .forEach((role, memberCount) -> embedBuilder.appendDescription("\n[" + i.incrementAndGet() + "] " + role.getName() + ": " + memberCount));

            i.set(0);
            embedBuilder.appendDescription("\n\n");

            embedBuilder.appendDescription(MessageProperty.MOST_POPULAR_LANGUAGE_GROUPS);
            RoleUtil.findRolesWithMemberCount(GroupCommand.LANGUAGE_PATTERN, guild)
                    .forEach((role, memberCount) -> embedBuilder.appendDescription("\n[" + i.incrementAndGet() + "] " + role.getName() + ": " + memberCount));

            commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            commandInfo.getMessage().delete().queue();
        });
    }
}
