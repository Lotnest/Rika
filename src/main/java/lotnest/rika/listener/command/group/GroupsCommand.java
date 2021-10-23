package lotnest.rika.listener.command.group;

import lotnest.rika.configuration.Command;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.MessageUtil;
import lotnest.rika.util.RoleUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class GroupsCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        MessageUtil.getCommandChannel(event).ifPresent(map -> map.forEach((channel, member) -> {
            if (member.getUser().isBot()) {
                return;
            }

            final String[] arguments = MessageUtil.getArguments(event.getMessage().getContentDisplay());
            if (arguments.length < 1) {
                return;
            }

            if (!arguments[0].equalsIgnoreCase(Command.GROUPS)) {
                return;
            }

            final Guild guild = event.getGuild();
            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, Command.GROUPS));

            CompletableFuture.runAsync(() -> {
                final AtomicInteger i = new AtomicInteger();
                embedBuilder.appendDescription("\n" + Message.MOST_POPULAR_EXERCISE_GROUPS);
                RoleUtil.findRolesWithMemberCount(GroupCommand.EXERCISE_PATTERN, guild)
                        .forEach((role, memberCount) -> embedBuilder.appendDescription("\n[" + i.incrementAndGet() + "] " + role.getName() + ": " + memberCount));

                i.set(0);
                embedBuilder.appendDescription("\n\n");

                embedBuilder.appendDescription(Message.MOST_POPULAR_LECTURE_GROUPS);
                RoleUtil.findRolesWithMemberCount(GroupCommand.LECTURE_PATTERN, guild)
                        .forEach((role, memberCount) -> embedBuilder.appendDescription("\n[" + i.incrementAndGet() + "] " + role.getName() + ": " + memberCount));

                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            });

            event.getMessage().delete().queue();
        }));
    }
}
