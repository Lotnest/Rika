package lotnest.rika.command.student;

import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.CommandProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.utils.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class PlanCommand extends Command {

    @Override
    public String getName() {
        return CommandProperty.PLAN;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        Message message = commandInfo.getMessage();
        MessageChannel channel = commandInfo.getChannel();
        EmbedBuilder embedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());

        embedBuilder.setDescription(MessageProperty.WORK_IN_PROGRESS);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
        message.delete().queue();
    }
}
