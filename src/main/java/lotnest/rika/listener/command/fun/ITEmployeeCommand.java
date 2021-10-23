package lotnest.rika.listener.command.fun;

import lotnest.rika.configuration.Command;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.CommandUtil;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class ITEmployeeCommand extends ListenerAdapter {

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

            if (!arguments[0].equalsIgnoreCase(Command.IT_EMPLOYEE)) {
                return;
            }

            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, Command.IT_EMPLOYEE));
            embedBuilder.setDescription(replacePlaceholders(Message.IT_EMPLOYEE_DESCRIPTION, member.getAsMention()));
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

            event.getMessage().delete().queue();
        }));
    }
}
