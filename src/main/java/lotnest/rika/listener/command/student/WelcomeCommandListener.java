package lotnest.rika.listener.command.student;

import lotnest.rika.configuration.Command;
import lotnest.rika.configuration.Id;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class WelcomeCommandListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        MessageUtil.getCommandChannel(event).ifPresent(map -> map.forEach((channel, member) -> {
            if (!channel.getId().equals(Id.VERIFICATION_CHANNEL)) {
                return;
            }

            final String[] arguments = MessageUtil.getArguments(event.getMessage().getContentDisplay());
            if (arguments.length < 1) {
                return;
            }

            if (!arguments[0].equalsIgnoreCase(Command.WELCOME)) {
                return;
            }

            if (!member.getId().equals("209254420192428032")) {
                return;
            }

            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            embedBuilder.setAuthor(Message.WELCOME_TITLE);
            embedBuilder.setDescription(Message.WELCOME_DESCRIPTION);
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

            event.getMessage().delete().queue();
        }));
    }
}
