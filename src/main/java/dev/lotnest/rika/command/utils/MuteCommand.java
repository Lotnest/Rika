package dev.lotnest.rika.command.utils;

import dev.lotnest.rika.command.Command;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.MemberUtils;
import dev.lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MuteCommand extends Command {

    @Override
    public String getName() {
        return CommandConstants.MUTE;
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
                embedBuilder.setDescription(MessageConstants.NO_USER_TO_MUTE);
            } else {
                MemberUtils.findRoleAndAddToMember(MessageConstants.MUTED_ROLE_NAME, mentionedMembers.get(0), MessageConstants.USER_MUTED, MessageConstants.ROLE_NOT_FOUND, MessageConstants.USER_ALREADY_MUTED, embedBuilder, channel);
            }
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            commandInfo.getMessage().delete().queue();
        }
    }
}
