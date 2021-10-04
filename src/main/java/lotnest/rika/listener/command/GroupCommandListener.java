package lotnest.rika.listener.command;

import lotnest.rika.configuration.Id;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.MemberUtil;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static lotnest.rika.configuration.Message.GROUP_COMMAND;
import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class GroupCommandListener extends ListenerAdapter {

    public static final Pattern EXERCISE_PATTERN = Pattern.compile("[0-9][0-9]c");
    public static final Pattern LECTURE_PATTERN = Pattern.compile("[0-9][0-9][0-9]l");

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        if (!channel.getId().equals(Id.COMMANDS_CHANNEL)) {
            return;
        }

        final Member member = event.getMember();
        if (member == null) {
            return;
        }

        final List<String> arguments = MessageUtil.getArguments(event.getMessage().getContentDisplay());
        if (arguments.size() < 2) {
            return;
        }

        if (!arguments.get(0).equalsIgnoreCase(GROUP_COMMAND)) {
            return;
        }

        final String arg1 = arguments.get(1);
        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, GROUP_COMMAND));

        MemberUtil.findRoleAndAddToMember(Arrays.asList(EXERCISE_PATTERN, LECTURE_PATTERN), arg1, member, Message.GROUP_COMMAND_ADDED_EXERCISE, Message.GROUP_COMMAND_EXERCISE_NOT_FOUND, Message.GROUP_COMMAND_INVALID_TYPE, embedBuilder, channel);

        event.getMessage().delete().queue();
    }
}
