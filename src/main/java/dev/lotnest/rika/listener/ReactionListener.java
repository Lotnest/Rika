package dev.lotnest.rika.listener;

import dev.lotnest.rika.configuration.IdConstants;
import dev.lotnest.rika.reaction.Reaction;
import dev.lotnest.rika.reaction.ReactionInfo;
import dev.lotnest.rika.reaction.impl.HobbyReaction;
import dev.lotnest.rika.reaction.impl.SpecializationReaction;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ReactionListener extends ListenerAdapter {

    private final Set<String> reactionMessageIds = new HashSet<>();
    private final Set<Reaction> reactions = new HashSet<>();

    @SneakyThrows
    public ReactionListener() {
        reactionMessageIds.add(IdConstants.SPECIALIZATION_MESSAGE);
        reactionMessageIds.add(IdConstants.HOBBY_MESSAGE);

        reactions.add(new SpecializationReaction());
        reactions.add(new HobbyReaction());
    }

    public ReactionInfo getReactionInfo(@NotNull GenericGuildMessageReactionEvent event) {
        return new ReactionInfo(
                event.getGuild(),
                event.getMember(),
                event.getMessageId(),
                event.getReaction().getReactionEmote().getEmoji(),
                event.getChannel()
        );
    }

    public boolean isReactionMessage(@NotNull String messageId) {
        return reactionMessageIds.contains(messageId);
    }

    @Override
    public void onGenericGuildMessageReaction(@NotNull GenericGuildMessageReactionEvent event) {
        ReactionInfo reactionInfo = getReactionInfo(event);
        Member member = reactionInfo.getMemberReacted();
        if (member == null || member.getUser().isBot()) {
            return;
        }

        String messageId = reactionInfo.getMessageId();
        if (messageId == null || !isReactionMessage(messageId)) {
            return;
        }

        if (event instanceof GuildMessageReactionAddEvent) {
            reactions.stream()
                    .filter(reaction -> reaction.getMessageId().equals(messageId))
                    .forEach(reaction -> reaction.handleReactionAdd(reactionInfo));
        } else if (event instanceof GuildMessageReactionRemoveEvent) {
            reactions.stream()
                    .filter(reaction -> reaction.getMessageId().equals(messageId))
                    .forEach(reaction -> reaction.handleReactionRemove(reactionInfo));
        }
    }
}
