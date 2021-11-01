package lotnest.rika.command.student;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static lotnest.rika.util.MemberUtil.findGroupRoleAndAddToMember;

public class GroupCommand extends Command {

    public static final Pattern EXERCISE_PATTERN = Pattern.compile(MessageProperty.GROUP_EXERCISE_REGEX);
    public static final Pattern LANGUAGE_PATTERN = Pattern.compile(MessageProperty.GROUP_LANGUAGE_REGEX);

    @Override
    public String getName() {
        return CommandProperty.GROUP;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(final @NotNull CommandInfo commandInfo) {
        final EmbedBuilder embedBuilder = MessageUtil.getCommandEmbedBuilder(commandInfo, getName());
        final Message message = commandInfo.getMessage();
        final MessageChannel channel = commandInfo.getChannel();

        if (commandInfo.getArgs().length < 2) {
            embedBuilder.setDescription(MessageProperty.GROUP_COMMAND_INVALID_TYPE);
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            message.delete().queue();
            return;
        }

        final String group = commandInfo.getArgs()[1];
        final Member executor = commandInfo.getExecutor();

        findGroupRoleAndAddToMember(
                group,
                executor,
                embedBuilder,
                channel
        );
        message.delete().queue();
    }
}
