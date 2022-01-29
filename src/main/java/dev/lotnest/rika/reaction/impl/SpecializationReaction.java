package dev.lotnest.rika.reaction.impl;

import dev.lotnest.rika.configuration.IdConstants;
import dev.lotnest.rika.reaction.Reaction;
import dev.lotnest.rika.reaction.ReactionInfo;
import dev.lotnest.rika.reaction.type.SpecializationType;
import org.jetbrains.annotations.NotNull;

public class SpecializationReaction extends Reaction {

    @Override
    public String getMessageId() {
        return IdConstants.SPECIALIZATION_MESSAGE;
    }

    @Override
    public void handleReactionAdd(final @NotNull ReactionInfo reactionInfo) {
        SpecializationType.fromEmoji(reactionInfo.getEmoji())
                .ifPresent(specializationType -> specializationType.addRole(reactionInfo.getMemberReacted()));
    }

    @Override
    public void handleReactionRemove(final @NotNull ReactionInfo reactionInfo) {
        SpecializationType.fromEmoji(reactionInfo.getEmoji())
                .ifPresent(specializationType -> specializationType.removeRole(reactionInfo.getMemberReacted()));
    }
}
