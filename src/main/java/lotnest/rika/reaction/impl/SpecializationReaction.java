package lotnest.rika.reaction.impl;

import lotnest.rika.configuration.IdProperty;
import lotnest.rika.reaction.Reaction;
import lotnest.rika.reaction.ReactionInfo;
import lotnest.rika.reaction.type.SpecializationType;
import org.jetbrains.annotations.NotNull;

public class SpecializationReaction extends Reaction {

    @Override
    public String getMessageId() {
        return IdProperty.SPECIALIZATION_MESSAGE;
    }

    @Override
    public void handleReactionAdd(final @NotNull ReactionInfo reactionInfo) {
        SpecializationType.fromEmoji(getMessageId())
                .ifPresent(specializationType -> specializationType.addRole(reactionInfo.getMemberReacted()));
    }

    @Override
    public void handleReactionRemove(final @NotNull ReactionInfo reactionInfo) {
        SpecializationType.fromEmoji(getMessageId())
                .ifPresent(specializationType -> specializationType.removeRole(reactionInfo.getMemberReacted()));
    }
}
