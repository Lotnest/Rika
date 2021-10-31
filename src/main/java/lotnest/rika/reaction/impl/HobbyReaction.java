package lotnest.rika.reaction.impl;

import lotnest.rika.configuration.IdProperty;
import lotnest.rika.reaction.Reaction;
import lotnest.rika.reaction.ReactionInfo;
import lotnest.rika.reaction.type.HobbyType;
import org.jetbrains.annotations.NotNull;

public class HobbyReaction extends Reaction {

    @Override
    public String getMessageId() {
        return IdProperty.HOBBY_MESSAGE;
    }

    @Override
    public void handleReactionAdd(final @NotNull ReactionInfo reactionInfo) {
        HobbyType.fromEmoji(getMessageId())
                .ifPresent(hobbyType -> hobbyType.addRole(reactionInfo.getMemberReacted()));
    }

    @Override
    public void handleReactionRemove(final @NotNull ReactionInfo reactionInfo) {
        HobbyType.fromEmoji(getMessageId())
                .ifPresent(hobbyType -> hobbyType.removeRole(reactionInfo.getMemberReacted()));
    }
}
