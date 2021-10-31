package lotnest.rika.manager;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import lombok.Getter;
import lombok.SneakyThrows;
import lotnest.rika.configuration.IdProperty;
import lotnest.rika.reaction.Reaction;
import lotnest.rika.reaction.ReactionInfo;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
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

        for (final PojoClass pojoClass : PojoClassFactory.enumerateClassesByExtendingType("lotnest.rika.reaction", Reaction.class, null)) {
            reactions.add((Reaction) pojoClass.getClazz().newInstance());
        }
    }

    public ReactionInfo getReactionInfo(@NotNull final GenericGuildMessageReactionEvent event) {
        return new ReactionInfo(
                event.getGuild(),
                event.getMember(),
                event.getMessageId(),
                event.getChannel()
        );
    }

    public boolean isReactionChannel(@NotNull final MessageChannel channel) {
        return reactionMessageIds.contains(channel.getId());
    }

    @Override
    public void onGenericGuildMessageReaction(@NotNull final GenericGuildMessageReactionEvent event) {
        final ReactionInfo reactionInfo = getReactionInfo(event);
        final Member member = reactionInfo.getMemberReacted();
        if (member == null || member.getUser().isBot()) {
            return;
        }

        if (!isReactionChannel(reactionInfo.getChannel())) {
            return;
        }

        if (event instanceof GuildMessageReactionAddEvent) {
            reactions.forEach(reaction -> reaction.handleReactionAdd(reactionInfo));
        } else if (event instanceof GuildMessageReactionRemoveEvent) {
            reactions.forEach(reaction -> reaction.handleReactionRemove(reactionInfo));
        }
    }
}
