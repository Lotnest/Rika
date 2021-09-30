package lotnest.rika.listener.reaction;

import lotnest.rika.configuration.Id;
import lotnest.rika.enums.Hobby;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HobbyReactionListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(@NotNull final GuildMessageReactionAddEvent event) {
        if (!event.getMessageId().equals(Id.HOBBY_MESSAGE)) {
            return;
        }
        Hobby.fromEmoji(event.getReaction().getReactionEmote().getEmoji())
                .ifPresent(hobby -> hobby.addRole(event.getMember()));
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull final GuildMessageReactionRemoveEvent event) {
        if (!event.getMessageId().equals(Id.HOBBY_MESSAGE)) {
            return;
        }

        final Member member = event.getMember();
        if (member != null) {
            Hobby.fromEmoji(event.getReaction().getReactionEmote().getEmoji())
                    .ifPresent(hobby -> hobby.removeRole(member));
        }
    }
}
