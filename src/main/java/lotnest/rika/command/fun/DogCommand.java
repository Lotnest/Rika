package lotnest.rika.command.fun;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.utils.MessageUtils;
import lotnest.rika.utils.Service;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class DogCommand extends Command implements Service {

    @Override
    public String getName() {
        return CommandProperty.DOG;
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
