package dev.lotnest.rika.command.utils;

import dev.lotnest.rika.Rika;
import dev.lotnest.rika.command.Command;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends Command {

    @Override
    public String getName() {
        return CommandConstants.PING;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UTILITY;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        embedBuilder.setDescription(MessageUtils.replacePlaceholders(MessageConstants.PING_DESCRIPTION, Rika.getJDA().getGatewayPing()));
        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
