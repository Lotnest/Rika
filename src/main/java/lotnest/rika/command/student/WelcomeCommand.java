package lotnest.rika.command.student;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.utils.MemberUtils;
import lotnest.rika.utils.MessageUtils;
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
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());

        if (MemberUtils.isExclusivePermissionUser(commandInfo.getExecutor().getId())) {
            embedBuilder.setAuthor(MessageProperty.WELCOME_TITLE);
            embedBuilder.setDescription(MessageProperty.WELCOME_DESCRIPTION);
        } else {
            embedBuilder.setDescription(MessageProperty.COMMAND_NO_PERMISSION);
        }

        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
