package dev.lotnest.rika.command.student;

import dev.lotnest.rika.command.AbstractCommand;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.MemberUtils;
import dev.lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class GroupCommand extends AbstractCommand {

    public static final Pattern EXERCISE_PATTERN = Pattern.compile(MessageConstants.GROUP_EXERCISE_REGEX);
    public static final Pattern LANGUAGE_PATTERN = Pattern.compile(MessageConstants.GROUP_LANGUAGE_REGEX);

    @Override
    public @NotNull String getName() {
        return CommandConstants.GROUP;
    }

    @Override
    public @NotNull CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        Message message = commandInfo.getMessage();
        MessageChannel channel = commandInfo.getChannel();

        if (commandInfo.getArgs().length < 2) {
            embedBuilder.setDescription(MessageConstants.GROUP_COMMAND_INVALID);
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            message.delete().queue();
            return;
        }

        String group = commandInfo.getArgs()[1];
        Member executor = commandInfo.getExecutor();

        MemberUtils.findGroupRoleAndAddToMember(group, executor, embedBuilder, channel);
        message.delete().queue();
    }
}
