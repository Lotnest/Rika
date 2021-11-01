package lotnest.rika.command.utility;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.util.MemberUtil;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class WelcomeCommand extends Command {

    @Override
    public String getName() {
        return CommandProperty.WELCOME;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(final @NotNull CommandInfo commandInfo) {
        final EmbedBuilder embedBuilder = MessageUtil.getCommandEmbedBuilder(commandInfo, getName());

        if (MemberUtil.isExclusivePermissionUser(commandInfo.getExecutor().getId())) {
            embedBuilder.setAuthor(MessageProperty.WELCOME_TITLE);
            embedBuilder.setDescription(MessageProperty.WELCOME_DESCRIPTION);
        } else {
            embedBuilder.setDescription(MessageProperty.COMMAND_NO_PERMISSION);
        }

        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
