package lotnest.rika.listener.command.group;

import lotnest.rika.configuration.Command;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static lotnest.rika.util.MemberUtil.findRoleAndAddToMember;
import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class GroupCommand extends ListenerAdapter {

    public static final Pattern EXERCISE_PATTERN = Pattern.compile("(\\d){2}[Cc]");
    public static final Pattern LECTURE_PATTERN = Pattern.compile("(\\d){3}[Ll]");

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        MessageUtil.getCommandChannel(event).ifPresent(map -> map.forEach((channel, member) -> {
            if (member.getUser().isBot()) {
                return;
            }

            final String[] arguments = MessageUtil.getArguments(event.getMessage().getContentDisplay());
            if (arguments.length < 2) {
                return;
            }

            if (!arguments[0].equalsIgnoreCase(Command.GROUP)) {
                return;
            }

            final String arg1 = arguments[1];
            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, Command.GROUP));

            if (EXERCISE_PATTERN.matcher(arg1).matches()) {
                findRoleAndAddToMember(EXERCISE_PATTERN, arg1, member, Message.GROUP_COMMAND_ADDED_EXERCISE, Message.GROUP_COMMAND_EXERCISE_NOT_FOUND, Message.GROUP_COMMAND_ALREADY_ASSIGNED, Message.GROUP_COMMAND_CHANGED_EXERCISE, embedBuilder, channel);
            } else if (LECTURE_PATTERN.matcher(arg1).matches()) {
                findRoleAndAddToMember(LECTURE_PATTERN, arg1, member, Message.GROUP_COMMAND_ADDED_LECTURE, Message.GROUP_COMMAND_LECTURE_NOT_FOUND, Message.GROUP_COMMAND_ALREADY_ASSIGNED, Message.GROUP_COMMAND_CHANGED_LECTURE, embedBuilder, channel);
            } else {
                embedBuilder.setDescription(Message.GROUP_COMMAND_INVALID_TYPE);
                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }

            event.getMessage().delete().queue();
        }));
    }
}
