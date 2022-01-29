package dev.lotnest.rika.command.student;

import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.MemberUtils;
import dev.lotnest.rika.command.Command;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class WelcomeCommand extends Command {

    @Override
    public String getName() {
        return CommandConstants.WELCOME;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());

        if (MemberUtils.isExclusivePermissionUser(commandInfo.getExecutor().getId())) {
            embedBuilder.setAuthor(MessageConstants.WELCOME_TITLE);
            embedBuilder.setDescription(MessageConstants.WELCOME_DESCRIPTION);
        } else {
            embedBuilder.setDescription(MessageConstants.COMMAND_NO_PERMISSION);
        }

        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
