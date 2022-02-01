package dev.lotnest.rika.command.fun;

import dev.lotnest.rika.command.Command;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.utils.MessageUtils;
import dev.lotnest.rika.utils.Service;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class DogCommand extends Command implements Service {

    @Override
    public String getName() {
        return CommandConstants.DOG;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.FUN;
    }

    @Override
    public void execute(final @NotNull CommandInfo commandInfo) {
        final EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());
        embedBuilder.setImage(getJsonValue("message"));
        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }

    @Override
    public String getServiceUrl() {
        return "https://dog.ceo/api/breeds/image/random";
    }
}
