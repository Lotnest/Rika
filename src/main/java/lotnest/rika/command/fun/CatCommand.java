package lotnest.rika.command.fun;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.util.MessageUtil;
import lotnest.rika.util.Service;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class CatCommand extends Command implements Service {

    @Override
    public String getName() {
        return CommandProperty.CAT;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.FUN;
    }

    @Override
    public void execute(final @NotNull CommandInfo commandInfo) {
        final EmbedBuilder embedBuilder = MessageUtil.getCommandEmbedBuilder(commandInfo, getName());
        embedBuilder.setImage(getJsonValue("file"));
        commandInfo.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        commandInfo.getMessage().delete().queue();
    }

    @Override
    public String getServiceUrl() {
        return "https://aws.random.cat/meow?ref=apilist.fun";
    }
}
