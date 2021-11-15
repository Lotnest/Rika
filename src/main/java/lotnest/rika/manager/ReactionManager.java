package lotnest.rika.manager;

import lombok.Getter;
import lombok.SneakyThrows;
import lotnest.rika.configuration.IdProperty;
import lotnest.rika.reaction.Reaction;
import lotnest.rika.reaction.ReactionInfo;
import lotnest.rika.reaction.impl.HobbyReaction;
import lotnest.rika.reaction.impl.SpecializationReaction;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ReactionManager extends ListenerAdapter {

    private final Set<String> reactionMessageIds = new HashSet<>();
    private final Set<Reaction> reactions = new HashSet<>();

    @SneakyThrows
    public ReactionManager() {
        reactionMessageIds.add(IdProperty.SPECIALIZATION_MESSAGE);
        reactionMessageIds.add(IdProperty.HOBBY_MESSAGE);

        reactions.add(new SpecializationReaction());
        reactions.add(new HobbyReaction());
    }

    public ReactionInfo getReactionInfo(final @NotNull GenericGuildMessageReactionEvent event) {
        return new ReactionInfo(
                event.getGuild(),
                event.getMember(),
                event.getMessageId(),
                event.getReaction().getReactionEmote().getEmoji(),
                event.getChannel()
        );
    }

    public boolean isReactionMessage(final @NotNull String messageId) {
        return reactionMessageIds.contains(messageId);
    }

    @Override
    public void onGenericGuildMessageReaction(final @NotNull GenericGuildMessageReactionEvent event) {
        final ReactionInfo reactionInfo = getReactionInfo(event);
        final Member member = reactionInfo.getMemberReacted();
        if (member == null || member.getUser().isBot()) {
            return;
        }

        final String messageId = reactionInfo.getMessageId();
        if (!isReactionMessage(messageId)) {
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
