package dev.lotnest.rika.reaction.impl;

import dev.lotnest.rika.configuration.IdConstants;
import dev.lotnest.rika.reaction.Reaction;
import dev.lotnest.rika.reaction.ReactionInfo;
import dev.lotnest.rika.reaction.type.HobbyType;
import org.jetbrains.annotations.NotNull;

public class HobbyReaction extends Reaction {

    @Override
    public String getMessageId() {
        return IdConstants.HOBBY_MESSAGE;
    }

    @Override
    public void handleReactionAdd(@NotNull ReactionInfo reactionInfo) {
        String emoji = reactionInfo.getEmoji();
        if (emoji != null) {
            HobbyType.fromEmoji(emoji)
                    .ifPresent(hobbyType -> hobbyType.addRole(reactionInfo.getMemberReacted()));
        }
    }

    @Override
    public void handleReactionRemove(@NotNull ReactionInfo reactionInfo) {
        String emoji = reactionInfo.getEmoji();
        if (emoji != null) {
            HobbyType.fromEmoji(emoji)
                    .ifPresent(hobbyType -> hobbyType.removeRole(reactionInfo.getMemberReacted()));
        }
    }
}
