package lotnest.rika.listener.command.utility;

import lotnest.rika.configuration.Command;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.CommandUtil;
import lotnest.rika.util.MemberUtil;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class MuteCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        CommandUtil.getCommandChannel(event).ifPresent(map -> map.forEach((channel, member) -> {
            if (member.getUser().isBot()) {
                return;
            }

            final net.dv8tion.jda.api.entities.Message message = event.getMessage();
            final String[] arguments = CommandUtil.getArguments(message.getContentDisplay());
            if (arguments.length < 1) {
                return;
            }

            if (!arguments[0].equalsIgnoreCase(Command.MUTE)) {
                return;
            }

            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            embedBuilder.setTitle(replacePlaceholders(Message.COMMAND_TITLE, Command.MUTE));

            if (MemberUtil.isModerator(event, true)) {
                final List<Member> mentionedMembers = message.getMentionedMembers();
                if (arguments.length < 2 || mentionedMembers.isEmpty()) {
                    embedBuilder.setDescription(Message.NO_USER_TO_MUTE);
                } else {
                    MemberUtil.findRoleAndAddToMember(Message.MUTED_ROLE_NAME, mentionedMembers.get(0), Message.USER_MUTED, Message.ROLE_NOT_FOUND, Message.USER_ALREADY_MUTED, embedBuilder, channel);
                }

                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

                event.getMessage().delete().queue();
            }
        }));
    }
}
