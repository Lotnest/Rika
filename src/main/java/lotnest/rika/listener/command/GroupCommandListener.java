package lotnest.rika.listener.command;

import lotnest.rika.configuration.Id;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static lotnest.rika.configuration.Message.GROUP_COMMAND;
import static lotnest.rika.util.MemberUtil.findRoleAndAddToMember;
import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class GroupCommandListener extends ListenerAdapter {

    public static final Pattern EXERCISE_PATTERN = Pattern.compile("[0-9][0-9]c");
    public static final Pattern LECTURE_PATTERN = Pattern.compile("[0-9][0-9][0-9]l");

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        if (!channel.getId().equals(Id.COMMANDS_CHANNEL) && !channel.getId().equals(Id.TESTING_CHANNEL)) {
            return;
        }

        final Member member = event.getMember();
        if (member == null) {
            return;
        }

        final String[] arguments = MessageUtil.getArguments(event.getMessage().getContentDisplay());
        if (arguments.length < 2) {
            return;
        }

        if (!arguments[0].equalsIgnoreCase(GROUP_COMMAND)) {
            return;
        }

        final String arg1 = arguments[1];
        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, GROUP_COMMAND));

        if (EXERCISE_PATTERN.matcher(arg1).matches()) {
            findRoleAndAddToMember(EXERCISE_PATTERN, arg1, member, Message.GROUP_COMMAND_ADDED_EXERCISE, Message.GROUP_COMMAND_EXERCISE_NOT_FOUND, Message.GROUP_COMMAND_ALREADY_ASSIGNED, Message.GROUP_COMMAND_CHANGED_EXERCISE, embedBuilder, channel);
        } else if (LECTURE_PATTERN.matcher(arg1).matches()) {
            findRoleAndAddToMember(LECTURE_PATTERN, arg1, member, Message.GROUP_COMMAND_ADDED_LECTURE, Message.GROUP_COMMAND_LECTURE_NOT_FOUND, Message.GROUP_COMMAND_ALREADY_ASSIGNED, Message.GROUP_COMMAND_CHANGED_LECTURE, embedBuilder, channel);
        } else {
            embedBuilder.setDescription(Message.GROUP_COMMAND_INVALID_TYPE);
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        }

        event.getMessage().delete().queue();
    }
}
