package lotnest.rika.command.fun;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import static lotnest.rika.utils.MessageUtils.replacePlaceholders;

public class ITEmployeeCommand extends Command {

    @Override
    public String getName() {
        return CommandProperty.IT_EMPLOYEE;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.FUN;
    }

    @Override
    public void execute(final @NotNull CommandInfo commandInfo) {
        final EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        embedBuilder.setDescription(replacePlaceholders(MessageProperty.IT_EMPLOYEE_DESCRIPTION, commandInfo.getExecutor().getAsMention()));
        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
