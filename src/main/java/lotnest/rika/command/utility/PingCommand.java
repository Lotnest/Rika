package lotnest.rika.command.utility;

import lotnest.rika.Rika;
import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends Command {

    @Override
    public String getName() {
        return CommandProperty.PING;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UTILITY;
    }

    @Override
    public void execute(final @NotNull CommandInfo commandInfo) {
        final EmbedBuilder embedBuilder = MessageUtil.getCommandEmbedBuilder(commandInfo, getName());
        embedBuilder.setDescription(MessageUtil.replacePlaceholders(MessageProperty.PING_DESCRIPTION, Rika.getJDA().getGatewayPing()));
        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
