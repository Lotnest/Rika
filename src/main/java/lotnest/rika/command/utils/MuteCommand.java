package lotnest.rika.command.utils;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.utils.MemberUtils;
import lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MuteCommand extends Command {

    @Override
    public String getName() {
        return CommandProperty.MUTE;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UTILITY;
    }

    @Override
    public void execute(final @NotNull CommandInfo commandInfo) {
        final EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        final MessageChannel channel = commandInfo.getChannel();

        if (MemberUtils.isModerator(commandInfo.getExecutor(), channel)) {
            final List<Member> mentionedMembers = commandInfo.getMessage().getMentionedMembers();
            if (commandInfo.getArgs().length < 2 || mentionedMembers.isEmpty()) {
                embedBuilder.setDescription(MessageProperty.NO_USER_TO_MUTE);
            } else {
                MemberUtils.findRoleAndAddToMember(MessageProperty.MUTED_ROLE_NAME, mentionedMembers.get(0), MessageProperty.USER_MUTED, MessageProperty.ROLE_NOT_FOUND, MessageProperty.USER_ALREADY_MUTED, embedBuilder, channel);
            }
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            commandInfo.getMessage().delete().queue();
        }
    }
}
