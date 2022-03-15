package dev.lotnest.rika.command.utils;

import dev.lotnest.rika.command.AbstractCommand;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class EclipseCommand extends AbstractCommand {

    @Override
    public @NotNull String getName() {
        return CommandConstants.ECLIPSE;
    }

    @Override
    public @NotNull CommandType getCommandType() {
        return CommandType.UTILITY;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        embedBuilder.setDescription(MessageConstants.ECLIPSE_MESSAGE);
        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }
}
