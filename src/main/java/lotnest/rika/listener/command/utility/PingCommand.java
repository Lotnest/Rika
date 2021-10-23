package lotnest.rika.listener.command.utility;

import lotnest.rika.configuration.Command;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.CommandUtil;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class PingCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        CommandUtil.getCommandChannel(event).ifPresent(map -> map.forEach((channel, member) -> {
            if (member.getUser().isBot()) {
                return;
            }

            final String[] arguments = CommandUtil.getArguments(event.getMessage().getContentDisplay());
            if (arguments.length < 1) {
                return;
            }

            if (!arguments[0].equalsIgnoreCase(Command.PING)) {
                return;
            }

            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, Command.PING));
            embedBuilder.setDescription(replacePlaceholders(Message.PING_DESCRIPTION, event.getJDA().getGatewayPing()));
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

            event.getMessage().delete().queue();
        }));
    }
}
