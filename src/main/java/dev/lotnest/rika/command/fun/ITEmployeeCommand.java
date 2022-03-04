package dev.lotnest.rika.command.fun;

import dev.lotnest.rika.command.AbstractCommand;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import static dev.lotnest.rika.utils.MessageUtils.replacePlaceholders;

public class ITEmployeeCommand extends AbstractCommand {

    @Override
    public @NotNull String getName() {
        return CommandConstants.IT_EMPLOYEE;
    }

    @Override
    public @NotNull CommandType getCommandType() {
        return CommandType.FUN;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        embedBuilder.setDescription(replacePlaceholders(MessageConstants.IT_EMPLOYEE_DESCRIPTION, commandInfo.getExecutor().getAsMention()));
        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
