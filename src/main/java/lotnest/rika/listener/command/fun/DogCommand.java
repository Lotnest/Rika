package lotnest.rika.listener.command.fun;

import lotnest.rika.configuration.Command;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.CommandUtil;
import lotnest.rika.util.MessageUtil;
import lotnest.rika.util.ServiceUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class DogCommand extends ListenerAdapter {

    public static final String SERVICE_URL = "https://dog.ceo/api/breeds/image/random";

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

            if (!arguments[0].equalsIgnoreCase(Command.DOG)) {
                return;
            }

            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, Command.DOG));
            embedBuilder.setImage(ServiceUtil.fetchStringFromUrl(SERVICE_URL, "message"));
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

            event.getMessage().delete().queue();
        }));
    }
}
